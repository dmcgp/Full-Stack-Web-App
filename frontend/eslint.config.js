// ESLint v9 flat config for React + TypeScript
import js from '@eslint/js'
import reactPlugin from 'eslint-plugin-react'
import tsParser from '@typescript-eslint/parser'
import tsPlugin from '@typescript-eslint/eslint-plugin'
import prettierConfig from 'eslint-config-prettier'

export default [
  js.configs.recommended,
  { ignores: ['dist/**', 'node_modules/**'] },
  {
    files: ['**/*.{ts,tsx,js,jsx}'],
    languageOptions: {
      parser: tsParser,
      ecmaVersion: 2022,
      sourceType: 'module',
      globals: {
        window: 'readonly',
        document: 'readonly',
        localStorage: 'readonly',
        JSX: 'readonly',
      },
    },
    plugins: {
      react: reactPlugin,
      '@typescript-eslint': tsPlugin,
    },
    rules: {
      'react/react-in-jsx-scope': 'off',
      'no-unused-vars': 'off',
      '@typescript-eslint/no-unused-vars': ['warn', { args: 'none' }],
    },
    settings: {
      react: { version: 'detect' },
    },
  },
  {
    files: ['**/__tests__/**/*.{ts,tsx,js,jsx}'],
    languageOptions: {
      globals: {
        test: 'readonly',
        expect: 'readonly',
      },
    },
  },
  {
    files: ['cypress/**/*.{ts,tsx,js,jsx}'],
    languageOptions: {
      globals: {
        cy: 'readonly',
        describe: 'readonly',
        it: 'readonly',
      },
    },
  },
  prettierConfig,
]
