module.exports = {
  presets: [
    ['@babel/preset-env', { targets: { node: 'current' } }],
    ['@babel/preset-react', { runtime: 'automatic' }],
    '@babel/preset-typescript',
  ],
  plugins: [
    // Transform import.meta to a global variable
    [
      'babel-plugin-transform-vite-meta-env',
      {
        replacements: [
          {
            importMetaEnv: 'import.meta.env',
            replacementModule: 'process.env',
          },
        ],
      },
    ],
  ],
};
