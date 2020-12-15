$(function() {
  let token = undefined;
  let replyId = undefined;

  let formAlert = $('#form-alert');
  let loginForm = $('#login-form');
  let rsvpForm = $('#rsvp-form');
  let attending = $('#attending');
  let questions = $('.question');
  let reload = $('#reload');

  reset();

  attending.on('change', function(e) {
    if (attending.val() == 'yes') {
      questions.show();
      questions.each(function(_, question) {
        $(question).attr('required', $(question).data('required'));
      });
    } else {
      questions.hide();
      questions.removeAttr('required');
    }
  });
  attending.trigger('change');

  loginForm.on('submit', function(e) {
    e.preventDefault();

    clearAlert();
    disableForm(loginForm, 'Sending...');

    logIn($('#username').val(), $('#password').val());
  });

  rsvpForm.on('submit', function(e) {
    e.preventDefault();

    clearAlert();
    disableForm(rsvpForm, 'Sending...');

    submitReply(getRsvpData());
  });

  $('#cancel,#reload').on('click', function(e) {
    reset();
  });

  function disableForm(form, message) {
    form.find('input,select,textarea').attr('disabled', 'disabled');
    form.find('input[type="submit"]').val(message);
  }

  function enableForm(form, action) {
    form.find('input,select,textarea').removeAttr('disabled');
    form.find('input[type="submit"]').val(action);
  }

  function clearAlert() {
    formAlert.hide();
  }

  function showAlert(message, color) {
    formAlert.text(message);
    formAlert.css('color', color);
    formAlert.show();
  }

  function getRsvpData() {
    let data = {};
    let fields = rsvpForm.find('input[name],select[name],textarea[name]');
    for (let i = 0; i < fields.length; i++) {
      data[fields[i].name] = $(fields[i]).val();
    }
    return data;
  }

  function setRsvpData(data) {
    let fields = rsvpForm.find('input[name],select[name],textarea[name]');
    for (let i = 0; i < fields.length; i++) {
      $(fields[i]).val(data[fields[i].name] ?? '');
    }
    attending.trigger('change');
  }

  function logIn(username, password) {
    let request = {
      identifier: username,
      password: password
    };

    loginForm.find('input[type="password"]').val('');

    $.ajax({
      url: loginForm.attr('action'),
      method: loginForm.attr('method'),
      dataType: 'json',
      data: request,
      success: function(data) {
        token = data.jwt;

        loginForm.hide();
        loadRsvpForm();
      },
      error: function() {
        enableForm(loginForm, 'Log In');
        showAlert('Invalid username or password!', '#c33');
      }
    });
  }

  function loadRsvpForm() {
    $.ajax({
      url: rsvpForm.attr('action'),
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + token
      },
      success: function(data) {
        if (data.length > 0) {
          replyId = data[0]._id;
          setRsvpData(data[0].data);
        } else {
          replyId = undefined;
          setRsvpData({});
        }

        rsvpForm.show();
      },
      error: function() {
        replyId = undefined;
        setRsvpData({});
        rsvpForm.show();
      }
    })
  }

  function submitReply(data) {
    let request = {
      data: data
    };

    let url = rsvpForm.attr('action');
    let method = 'POST';
    if (replyId) {
      url = url + '/' + replyId;
      method = 'PUT';
    }

    $.ajax({
      url: url,
      method: method,
      headers: {
        'Authorization': 'Bearer ' + token
      },
      dataType: 'json',
      data: request,
      success: function(data) {
        replyId = data._id;

        enableForm(rsvpForm, 'Send Reply');
        showAlert('Submitted Successfully!', '#3c3');
        rsvpForm.hide();
        reload.show();
      },
      error: function() {
        enableForm(rsvpForm, 'Send Reply');
        showAlert('Failed to submit!', '#c33');
      }
    });
  }

  function reset() {
    token = undefined;
    replyId = undefined;

    clearAlert();
    reload.hide();
    rsvpForm.hide();
    enableForm(loginForm, 'Log In');
    loginForm.show();
  }
});