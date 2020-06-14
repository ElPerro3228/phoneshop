$("button[name='add-to-cart']").click(function(){
    var $this = $(this);
    var phoneId = $this.data('phoneId');
    var cartItem = {};
    cartItem["quantity"] = $("#" + phoneId).val();
    cartItem["phoneId"] = phoneId;
    $this.siblings(".error").text("");

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "ajaxCart",
        data : JSON.stringify(cartItem),
        dataType : 'json',
        success : function(data) {
            $("#cartPrice").text("items: " + data.quantity + ", price: $ " + data.cartPrice);
        },
        error: function (request, status, error) {
            var e = JSON.parse(request.responseText);
            $this.siblings(".error").text(e.message);
        }
    });
});