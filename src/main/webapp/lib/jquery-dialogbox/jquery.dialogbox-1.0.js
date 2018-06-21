/**
 * jquery.dialogbox v1.0
 * Copyright (C) 2015 Afraware Technology Inc.
 * Licensed under the MIT license.
 * Created by Milad Rahimi [http://miladrahimi.com] on 20/2/2015.
 */
(function ($) {
    // dialogbox Method
    $.fn.dialogbox = function ($options, $callback) {
        // Create a unique ID
        var $id = '#dialogbox_1';
        for(var $i = 2; $($id).length; $i++) $id = '#dialogbox_' + $i;
        // Add dialogbox to element
        var $DOM_ID = $id.substr(1);
        this.append('<!-- Afraware Dialogbox -->' + '<div id="' + $DOM_ID + '">' +
        '<div class="dialogbox-back">' + '<div class="dialogbox-main">' +
        '<div class="dialogbox-close">x</div>' + '<div class="dialogbox-title"></div>' +
        '<div class="dialogbox-message"></div>' + //'<div class="dialogbox-prompt"></div>' +
        '<div class="dialogbox-button-bar"></div>' + '</div>' + '</div>' + '</div>');
        // Options
        var $settings = $.extend({
			buttons:[],
            type: 'normal',
            title: 'Message',
            message: 'WOW! It\'s a message!',
            direction: 'ltr',
            placeholder: null,
            options: '',
            splitter: ','
        }, $options);
        // Set Direction
        switch($settings['direction']) {
            case 'rtl':
                $($id + ' .dialogbox-main').css({
                    'border-right-width': '5px'
                });
                $($id + ' .dialogbox-close').css({
                    'left': '15px',
                    'right': 'none'
                });
                $($id + ' .dialogbox-main').css({
                    'direction': 'rtl'
                });
                break;
            default:
                $($id + ' .dialogbox-main').css({
                    'border-left-width': '5px'
                });
                $($id + ' .dialogbox-close').css({
                    'right': '15px',
                    'left': 'none'
                });
                $($id + ' .dialogbox-main').css({
                    'direction': 'ltr'
                });
                break;
        }
		if($settings.buttons.length > 0)
		{
			$($id + ' .dialogbox-button-bar').html('');
			for(var btnCount=0;btnCount <$settings.buttons.length; btnCount++)
			{
				$($id + ' .dialogbox-button-bar').append('<div class="dialogbox-btn" id="dialogbox_btn'+btnCount+'">'+$settings.buttons[btnCount].Text+'</div>')
				if($settings.buttons[btnCount].callback && $settings.buttons[btnCount].callback.constructor && $settings.buttons[btnCount].callback.call && $settings.buttons[btnCount].callback.apply) 
				{
					//.unbind("click");
					$($id + ' #dialogbox_btn'+btnCount).on("click", function (e) {
						var indexBtnCount = parseInt($(e.target).attr("id").replace("dialogbox_btn",""));
						if(!isNaN(indexBtnCount))
						{
							
							$settings.buttons[indexBtnCount].callback($id);
							if($settings.buttons[indexBtnCount].ClickToClose)
							{
								$($id).fadeOut("slow");
								$($id).remove();
							}
						}
					});
				}
			}
		}
		
        // Draw Details
        var $border_color = 'rgb(90,90,90)';
        switch($settings['type']) {
            case 'success':
                $border_color = 'rgb(39,174,96)';
                break;
            case 'warning':
            case 'tip':
                $border_color = 'rgb(243,156,18)';
                break;
            case 'error':
                $border_color = 'rgb(192,57,43)';
                break;
        }
        $($id + ' .dialogbox-main').css({
            'border-color': $border_color
        });
        // Set Title and Message
        $($id + ' .dialogbox-title').html($settings['title']);
        $($id + ' .dialogbox-message').html($settings['message']);
        // Display
        $($id + ' .dialogbox-back').fadeIn('fast');
        $($id + ' .dialogbox-main').fadeIn('fast');
		
		$($id + ' .dialogbox-close').on("click",  function () {
                $($id).fadeOut("slow");
                $($id).remove();
            });
		/*
        // Callbacks
        if($callback && $callback.constructor && $callback.call && $callback.apply) {
            // Close
            $(document).on("click", $id + ' .dialogbox-close', function () {
                $($id).fadeOut("slow");
                $callback("close");
                $($id).remove();
            });
            // Buttons
            if($settings['type'] == 'text') {
                $(document).on('click', $id + ' .dialogbox-btn', function () {
                    var $btn = 'ok';
                    var $return = $($id + ' input').val();
                    if($return == '') {
                        $btn = "close";
                        $callback($btn);
                        $($id).remove();
                    } else {
                        $callback($btn, $return);
                        $($id).remove();
                    }
                });
            } else if($settings['type'] == 'select') {
                $(document).on('click', $id + ' .dialogbox-btn', function () {
                    var $btn = "ok";
                    var $return = $($id + ' select').val();
                    if($return == "_placeholder") {
                        $btn = "close";
                        $callback($btn);
                        $($id).remove();
                    } else {
                        $callback($btn, $return);
                        $($id).remove();
                    }
                });
            } else {
                $(document).on('click', $id + ' .dialogbox-btn', function () {
                    $callback($(this).html().toLowerCase());
                    $($id).remove();
                });
            }
        } else {
            $(document).on("click", $id + ' .dialogbox-close', function () {
                $($id).remove();
            }).on("click", $id + ' .dialogbox-btn', function () {
                $($id).remove();
            });
        }*/
    };
}(jQuery));
// The End!