(function ($) {

    $.fn.dropdown = function (_imgDivId, myCallback) {

        var _selProject = '';
        var self = this;

        var imageDiv = _imgDivId;

        var resultDiv = self[0].id;
        var imgDivPos = $("#" + resultDiv).position();
        var imgDivWid = $("#" + resultDiv).width();


        $("#" + resultDiv).append("<span id='img_" + resultDiv + "'  class='imgdd'></span> <span id='btn_" + resultDiv + "'  class='arrow'></span>");

        $("#btn_" + resultDiv).click(function () {

            var position = $("#" + resultDiv).position();

            var height = $("#" + resultDiv).height();

            if ($("#" + imageDiv).is(':visible')) {

                $("#" + imageDiv).css({ "display": "none" });
            } else {
                $("#" + imageDiv).css({ "left": position.left + "px", "top": (position.top + height + 8) + "px" });

                $("#" + imageDiv).css({ "display": "block" });
            }



        });



        $("#" + imageDiv + " ul li").live("click", function () {

            $("#" + imageDiv + " ul li").removeClass('selectThumbnailImgame');
            _selected = this.id;
            var imgUrl = $(this).children().children().attr('src');
            var id =  $(this).children().children().attr('id');
//            if (imgUrl.indexOf('../Content') == -1) {
//                imgUrl = imgUrl.substring(imgUrl.indexOf('/Content'));
//                imgUrl = ".." + imgUrl;
//            }

            $("#" + imageDiv).css({ "display": "none" });
            $("#" + _selected).addClass('selectThumbnailImgame');

            //$(".imgdd").css("background-image", 'url("' + imgUrl + '")');
            $("#img_" + resultDiv).css("background-image", 'url("' + imgUrl + '")');

            $("#titleText").html(_selected);

            var defaultStyle = $(this).children().children().attr('alt');
            if (defaultStyle == 'default') {

                myCallback('default');

            } else {

                myCallback(imgUrl, id);
            }

        });



    };
})(jQuery); 


