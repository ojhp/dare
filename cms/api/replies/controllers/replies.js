'use strict';

const { parseMultipartData, sanitizeEntity } = require('strapi-utils');

/**
 * Read the documentation (https://strapi.io/documentation/v3.x/concepts/controllers.html#core-controllers)
 * to customize this controller
 */

module.exports = {
  async find(ctx) {
    let entities;

    ctx.query.user = ctx.state.user.id;

    if (ctx.query._q) {
      console.debug('FIND');
      entities = await strapi.services.replies.find(ctx.query);
    } else {
      console.debug('SEARCH');
      entities = await strapi.services.replies.search(ctx.query);
    }

    return entities.map(entity =>
      sanitizeEntity(entity, { model: strapi.models.replies }));
  },

  async create(ctx) {
    let entity;
    if (ctx.is('multipart')) {
      const { data, files } = parseMultipartData(ctx);
      data.user = ctx.state.user.id;
      entity = await strapi.services.replies.create(data, { files });
    } else {
      ctx.request.body.user = ctx.state.user.id;
      entity = await strapi.services.replies.create(ctx.request.body);
    }
    return sanitizeEntity(entity, { model: strapi.models.replies });
  },

  async update(ctx) {
    const { id } = ctx.params;

    let entity;

    const [reply] = await strapi.services.replies.find({
      id: ctx.params.id,
      'user.id': ctx.state.user.id
    });

    if (!reply) {
      return ctx.unauthorized(`You can't update this entry`);
    }

    if (ctx.is('multipart')) {
      const { data, files } = parseMultipartData(ctx);
      data.user = ctx.state.user.id;
      entity = await strapi.services.replies.update({ id }, data, { files });
    } else {
      ctx.request.body.user = ctx.state.user.id;
      entity = await strapi.services.replies.update({ id }, ctx.request.body);
    }
    return sanitizeEntity(entity, { model: strapi.models.replies });
  }
};
