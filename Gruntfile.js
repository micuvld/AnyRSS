module.exports = function (grunt) {

    let DEV_PATH = 'dev_webapp';
    let LIVE_PATH = 'src/main/webapp';

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        copy: {
            main: {
                expand: true,
                cwd: DEV_PATH,
                src: ['content/**/*', '!index.html'],
                dest: LIVE_PATH
            }
        },
        concat: {
            options: {
                separator: '\n'
            },
            js: {
                src: [
                    DEV_PATH + '/lib//js/angular.min.js',
                    DEV_PATH + '/lib/js/angular_dependencies/*'
                ],
                dest: LIVE_PATH + '/lib/dependencies.js'
            },
            css: {
                src: [DEV_PATH + '/lib/css/*'],
                dest: LIVE_PATH + '/lib/dependencies.css'
            }
        },
        includeSource: {
            options: {
                basePath: LIVE_PATH,
                baseUrl: '../../',
                ordering: 'top-down'
            },
            myTarget: {
                files: {
                    'src/main/webapp/WEB-INF/public/index.html': 'dev_webapp/index.html'
                }
            }
        },
        watch: {
            any: {
                files: [DEV_PATH + '/**/*'],
                tasks: ['copy', 'concat', 'includeSource']
            }
        }
    });

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-include-source');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-watch');

    // Default task(s).
    grunt.registerTask('serve', function (target) {
        grunt.task.run('copy');
        grunt.task.run('concat');
        grunt.task.run('includeSource');
        grunt.task.run('watch');
    })
};