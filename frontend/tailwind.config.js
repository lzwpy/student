/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        cream: "#fffaf5",
        peach: "#fff1e7",
        ink: "#1f2937",
        mint: {
          DEFAULT: "#1ec8b5",
          soft: "#dffaf4",
          dark: "#0f766e"
        },
        gold: {
          DEFAULT: "#f6c453",
          soft: "#fff7dd",
          dark: "#a16207"
        },
        rosey: {
          DEFAULT: "#ff8fa3",
          soft: "#fff1f3",
          pale: "#ffe8ed",
          dark: "#be123c"
        },
        line: "#f1e7dd"
      },
      boxShadow: {
        float: "0 14px 28px rgba(252, 199, 159, 0.16)",
        soft: "0 10px 18px rgba(31, 41, 55, 0.06)"
      },
      borderRadius: {
        "4xl": "2rem",
        "5xl": "2.25rem"
      },
      fontFamily: {
        display: ["Microsoft YaHei", "PingFang SC", "sans-serif"]
      }
    }
  },
  plugins: []
};
