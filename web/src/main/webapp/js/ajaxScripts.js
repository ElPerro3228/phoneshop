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
        url : "/phoneshop/ajaxCart",
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

$("button[name='delete-from-cart']").click(function(){
    var $this = $(this);
    var $parent = $this.closest(".cart-item");
    var phoneId = $this.data('phoneId');
    var cartItem = {};
    cartItem["quantity"] = $("#" + phoneId).val();
    cartItem["phoneId"] = phoneId;
    $this.siblings(".error").text("");

    $.ajax({
        type : "DELETE",
        contentType : "application/json",
        url : "/phoneshop/ajaxCart",
        data : JSON.stringify(cartItem),
        dataType : 'json',
        success : function(data) {
            $("#cartPrice").text("items: " + data.quantity + ", price: $ " + data.cartPrice);
            $("#total-price").text("Price: $ " + data.cartPrice + "");
            $parent.remove();
        },
        error: function (request, status, error) {
            var e = JSON.parse(request.responseText);
            $this.siblings(".error").text(e.message);
        }
    });
});

$("button[name='update-cart']").click(function(){
    var quantityInputs = $('input[name="cart-item-quantity"]');
    var cartItems = [];
    var size = quantityInputs.size();

    for (var i = 0; i < size; i++) {
        cartItems.push({
            phoneId: quantityInputs.get(i).id,
            quantity: quantityInputs.get(i).value
        });
        $("#" + quantityInputs.get(i).id).siblings(".error").text("");
    }

    var cartPageData = {
        cartItems: cartItems,
        errorsMap: new Map()
    };

    $.ajax({
        type : "PUT",
        contentType : "application/json",
        url : "/phoneshop/ajaxCart",
        data : JSON.stringify(cartPageData),
        dataType : 'json',
        success : function(data) {
            $("#cartPrice").text("items: " + data.quantity + ", price: $ " + data.cartPrice);
            $("#total-price").text("Price: $ " + data.cartPrice + "");
        },
        error: function (request, status, error) {
            var e = JSON.parse(request.responseText);
            $.each(e, function(key, value) {
                $("#" + key).siblings(".error").text(value);
            });
        }
    });
});