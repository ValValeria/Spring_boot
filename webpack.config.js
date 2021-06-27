const { VueLoaderPlugin } = require('vue-loader');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const path = require('path');

module.exports = {
    mode: "development",
    output:{
        path: path.resolve(path.join('src', 'main', 'resources','public')),
        filename: 'index.js'
    },
    entry:path.resolve(path.join(__dirname, 'vuejs', 'index.js')),
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.js$/,
                loader: 'babel-loader'
            },
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.scss$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: ['css-loader', 'sass-loader']
                })
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin(),
        new ExtractTextPlugin('styles.css')
    ],
    resolve: {
        extensions: ['.vue', '.json','.js','.scss','.sass','.css'],
    },
}