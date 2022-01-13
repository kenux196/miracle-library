const path = require('path');

module.exports = {
  outputDir: path.resolve(__dirname, '../main/resources/static'),
  devServer: {
    overlay: false,
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true,
      },
    },
  },
};
