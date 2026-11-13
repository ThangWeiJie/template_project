import {defineConfig} from 'vite'
import tanstackRouter from "@tanstack/router-plugin/vite";
import tailwindcss from "@tailwindcss/vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  server: {
    port: 3000,
  },
  resolve: {
    tsconfigPaths: true,
  },
  plugins: [
    tanstackRouter({target: "react", autoCodeSplitting: true}),
    tailwindcss(),
    react(),
  ],
})