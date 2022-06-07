// webpack.config.js
import path from 'path';
import { fileURLToPath } from 'url';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import MiniCssExtractPlugin from 'mini-css-extract-plugin';

const dirname = path.dirname(fileURLToPath(import.meta.url));

const config = {
  entry: './src/App.js',
  resolve: {
          extensions: ['js','jsx']
      },
  module: {
    rules: [
      {
        test: /\.('js','jsx')$/,
        exclude: /(node_modules|pages)/,
        use: {
          loader: 'babel-loader',
        },
      },
      {
        test: /\.css$/,
        use: [
          { loader: MiniCssExtractPlugin.loader },
          {
            loader: 'css-loader',
            options: { import: true },
          },
        ],
      },
      {
        test: /\.png$/,
        type: 'asset/resource',
      },
    ],
  },
  plugins: [new HtmlWebpackPlugin({ template: './src/index.html' }), new MiniCssExtractPlugin()],
  devtool: 'eval-cheap-module-source-map',
  target: 'web',
  devServer: {
    compress: true,
    hot: false,
    historyApiFallback: true,
    liveReload: true,
    open: true,
    port: 7700,
  },
};

export default config;