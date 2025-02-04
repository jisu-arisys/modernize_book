import { fileURLToPath, URL } from "url";
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vuetify from "vite-plugin-vuetify";


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vuetify({
      autoImport: true,
      //styles: "expose",
    }),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {},
    },
  },
  optimizeDeps: {
    exclude: ['vuetify'],
    entries: [
      './src/**/*.vue',
    ],
  },
  build: {
    outDir : '../src/main/resources/static',
    chunkSizeWarningLimit: 5000,
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
});
