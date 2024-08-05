const { defineConfig } = require('@vue/cli-service');
const webpack = require('webpack'); // webpack 모듈 가져오기

module.exports = defineConfig({
  configureWebpack: {
    plugins: [
      new webpack.DefinePlugin({
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: JSON.stringify(false),
      }),
    ],
  },
});