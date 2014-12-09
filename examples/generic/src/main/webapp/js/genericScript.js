/**
 * This file will contain generic script content
 * [i.e. script that needs to be executed to create various HTML5 elements, for it to work properly for IE]
 */

(function() {
    var eleCreateForIE = ["article", "aside", "details", "header", "hgroup", "nav", "section", "summary"],
        length = eleCreateForIE.length;
    
    while(length--){
        document.createElement(eleCreateForIE[length]);
    }
})();