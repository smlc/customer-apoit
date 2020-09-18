const gulp = require("gulp");
const babel = require("gulp-babel");
const watch = require("gulp-watch");
const browserSync = require("browser-sync").create();
const environments = require("gulp-environments");
const uglifycss = require("gulp-uglifycss");
const terser = require("gulp-terser");
const postcss = require("gulp-postcss");
const purgecss = require("gulp-purgecss");
const replace = require('gulp-replace');

const productionHost = "https://carwashdiff.herokuapp.com/api/appointment";
const production = environments.production;

gulp.task("watch", () => {
  browserSync.init({
    proxy: "localhost:8080",
  });

  gulp.watch(
    ["src/main/resources/**/*.html"],
    gulp.series("copy-html-and-reload")
  );
  gulp.watch(
    ["src/main/resources/**/*.css"],
    gulp.series("copy-css-and-reload")
  );
  gulp.watch(["src/main/resources/**/*.js"], gulp.series("copy-js-and-reload"));
});

gulp.task("copy-html", () =>
  gulp.src(["src/main/resources/**/*.html"])
      .pipe(production(replace('http://localhost:8080/api/appointment', productionHost)))
      .pipe(gulp.dest("target/classes/"))
);

gulp.task("copy-font", () =>
  gulp
    .src(["src/main/resources/static/css/fontstyle.css"])
    .pipe(production(uglifycss()))
    .pipe(gulp.dest("target/classes/static/css"))
);

gulp.task("copy-css", () =>
  gulp
    .src(["src/main/resources/static/css/application.css"])
    .pipe(postcss([require("tailwindcss"), require("autoprefixer")]))
    .pipe(
      production(
        purgecss({
          content: ["src/main/resources/templates/**/*.html"],
          extractors: [
            {
              extractor: (content) => {
                // Capture as liberally as possible, including things like `h-(screen-1.5)`
                const broadMatches =
                  content.match(/[^<>"'`\s]*[^<>"'`\s:]/g) || [];

                // Capture classes within other delimiters like .block(class="w-1/2") in Pug
                const innerMatches =
                  content.match(/[^<>"'`\s.()]*[^<>"'`\s.():]/g) || [];

                return broadMatches.concat(innerMatches);
              },
              extensions: ["html"],
            },
          ],
        })
      )
    )
    .pipe(production(uglifycss()))
    .pipe(gulp.dest("target/classes/static/css"))
);

gulp.task("copy-js", () =>
  gulp
    .src(["src/main/resources/**/*.js"])
    .pipe(babel())
    .pipe(production(terser()))
    .pipe(gulp.dest("target/classes/"))
);

gulp.task("copy-html-and-reload", gulp.series("copy-html", reload));
gulp.task("copy-css-and-reload", gulp.series("copy-css", reload));
gulp.task("copy-js-and-reload", gulp.series("copy-js", reload));
gulp.task("copy-font-and-reload", gulp.series("copy-font", reload));

gulp.task(
  "build",
  gulp.series("copy-html", "copy-css", "copy-js", "copy-font")
);
gulp.task("default", gulp.series("watch"));

function reload(done) {
  browserSync.reload();
  done();
}
