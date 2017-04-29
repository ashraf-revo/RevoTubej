(function ($) {
    'use strict';

    $(document).on('click.search', '.search-modal', function(e) {
        e.preventDefault();
        $('#search-modal').modal('show');
    });

    $(document).on('click', '#search-result a', function(e) {
        $('#search-modal').modal('hide');
    });

    var timeoutID = null;
    function ajax_search(query) {
        var $wrap = $('#search-result')
        $.ajax({
            type : 'post',
            url : ajax.ajax_url,
            data : {
                action : 'ajax_search_results',
                query : query,
                nonce: ajax.nonce
            },
            beforeSend: function() {
                $('#search-loading').removeClass('hide');
                $wrap.html('');
            },
            success : function( response ) {
                $('#search-loading').addClass('hide');
                $wrap.html( response );
            }
        });
    }

    $(document).on('keyup', '#searchform input', function(e){
        var $target = $(this);
        if(!$target.val()){
            return;
        }
        clearTimeout(timeoutID);
        timeoutID = setTimeout(function() { ajax_search($target.val()); }, 500);
    });
    $(document).on('click.more', '.btn-more', function(e) {
        var $dp = $(this).next('.dropdown-menu');
        if( $dp.html() == "" ){
            $dp.append($('#item-dropdown-tpl').html());
        }

        if( (e.pageY + $dp.height() + 60) > $('body').height() ){
            $dp.parent().addClass('dropup');
        }

        if( e.pageX < $dp.width() ){
            $dp.removeClass('pull-right');
        }

    });

    $(document).on('click.share', '.btn-share', function(e) {
        var link = $(this).closest('.item').find('.item-title a').attr('href');
        if(!link){
            link = location.href;
        }

        $('#share-modal').modal('show');
        $('#share-list a').each( function(index) {
            $(this).attr( 'href', $(this).attr('data-url') + encodeURIComponent( link ) );
        });
        $('#share-url').val( link );
    });

})(jQuery);
