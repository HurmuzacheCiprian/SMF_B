/**
 * Created by cipriach on 03.12.2015.
 */
module.exports = function(grunt) {

    grunt.initConfig({
        wiredep: {
            task: {
                src: [
                    'main/*.html'   // .html support...
                ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-wiredep');

    grunt.registerTask('default',['wiredep']);


};