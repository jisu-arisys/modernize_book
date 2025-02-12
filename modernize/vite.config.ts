

import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      // "/api"로 시작하는 요청을 프록시 처리
      '/api': {
        // 백엔드 서버 주소
        target: 'http://localhost:8080',
        // CORS 문제 해결을 위해 필요
        changeOrigin: true,
        // 경로에서 '/api' 제거
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
