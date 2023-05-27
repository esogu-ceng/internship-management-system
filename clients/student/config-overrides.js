const path = require('path')

module.exports = {
  paths: (paths, env) => {
    paths.appPublic = path.resolve(__dirname, 'html')
    paths.appHtml = path.resolve(__dirname, 'html/index.html')
    return paths
  },
}
