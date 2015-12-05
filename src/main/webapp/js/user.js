/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url = "user";

function clickBack() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickBack"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("index");
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickInformation() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickInform"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickChangeUserData() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickUserChangeData"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickChange(changeSelect) {
    var select = null;
    for (var i = 0 ; i < changeSelect.length; i++) {
        if (changeSelect[i].checked) {
            select = changeSelect[i].value;
            break;
        }
    }
    if (select != null && select != "") {
        $.ajax({
            dataType: "xml",
            url: url,
            type: "POST",
            data: {
                action: select
            },
            success: function (data) {
                if ("ok" === $(data).find("result").text().toLowerCase()) {
                    window.location.replace(url);
                }
            },
            error: function () {
                alert("Error while send data.");
            }
        });
    } else {
        alert("Make your choice.");
    }
}

function validPassword(oldPassword, newPassword, confirmPassword) {
    if (oldPassword.length > 0) {
        if (newPassword.length > 0) {
            if (confirmPassword.length > 0) {
                if (newPassword === confirmPassword) {
                    clickChangePassword(hex_md5(oldPassword), hex_md5(newPassword));
                } else {
                    alert("NewPassword not equal confirmPassword");
                }
            } else {
                alert("Please enter confirm password");
            }
        } else {
            alert("Please enter new password");
        }
    } else {
        alert("Please enter password");
    }
}

function clickChangePassword(oldPassword, newPassword) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickChangePassword",
            oldPassword: oldPassword,
            newPassword: newPassword
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                $("#password").val("");
                $("#newPassword").val("");
                $("#confirmPassword").val("");
                alert("Your password is changed");
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

var oldName;
var oldSecondName;
var oldPhone;


function validUserData(newName, newSecondName, newPhone) {
    var name;
    var secondName;
    var phone;

    if (newName != null && newName != "") {
        if (newName != oldName) {
            if (newName.length >=3) {
                name = newName;
            } else {
                name = null;
                alert("Short name.");
            }
        } else {
            name = oldName;
        }
    } else {
        name = oldName;
    }

    if (newSecondName != null && newSecondName != "") {
        if (newSecondName != oldSecondName) {
            if (newSecondName >= 2) {
                secondName = newSecondName;
            } else {
                secondName = null;
                alert("Short second name.");
            }
        } else {
            secondName = oldSecondName;
        }
    } else {
        secondName = oldSecondName;
    }

    if (newPhone != null && newPhone != "") {
        if (newPhone != oldPhone) {
            phone = newPhone;
        } else {
            phone = oldPhone;
        }
    } else {
        phone = oldPhone;
    }

    if (name != null && secondName != null) {
        clickChangeUser(name, secondName, phone);
    }
}

function clickChangeUser(name, secondName, phone) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "sendNewUserData",
            name: name,
            secondName: secondName,
            phone: phone
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                $("#changeName").val("");
                $("#changeSecondName").val("");
                $("#changePhone").val("");
                alert("Your user data is changed. Please re login");
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function validEmail(email) {
    if(isEmail(email)) {
        clickChangeEmail(email);
    } else {
        alert("Incorrect Email address.");
    }
}

function isEmail(checkEmail) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(checkEmail);
}

function clickChangeEmail(checkEmail) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickChangeEmail",
            checkEmail: checkEmail
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                $("#checkEmail").val("");
                alert("On an email sent your letter of confirmation.");
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickAddLotPage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickAddLotPage"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function validLot(title, description, endDate, hours, minutes, startPrice, buyOutPrice) {
    var timeInMs = Date.now();
    var timeZone = - new Date().getTimezoneOffset()/60;
    var timeCloseLot = Date.parse(endDate) + (hours - timeZone)* 3600000 + minutes * 60000;
    var maxCloseDate = Date.now() + 1209600000;
    if (title.length > 0 && description.length > 0 && endDate.length > 0 &&
            hours.length > 0 && minutes.length > 0 &&
            startPrice.length > 0 && buyOutPrice.length > 0) {
        if (title.length < 3) {
            alert("Short title.");
        } else if(description.length < 3) {
            alert("Short description.");
        } else if (timeCloseLot < timeInMs || timeCloseLot > maxCloseDate) {
            if (timeCloseLot < timeInMs) {
                alert("Incorrect date.");
            } else if (timeCloseLot > maxCloseDate) {
                alert("The end date not more than two weeks.");
            }
        } else {
            clickAddLot(title, description, Date.parse(endDate), hours, minutes, startPrice, buyOutPrice);
        }
    } else {
        alert("Please fill in all required fields.");
    }
}

function clickAddLot(title, description, endDate, hours, minutes, startPrice, buyOutPrice) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickAddLot",
            title: title,
            description: description,
            endDate: endDate,
            hours: hours,
            minutes: minutes,
            startPrice: startPrice,
            buyOutPrice: buyOutPrice
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickShowLotsPurchasedPage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "showLotsPurchased"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickFollowingProductsPage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "followingProducts"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickSoldGoodsPage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickSoldGoodsPage"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickGoodsForSalePage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickGoodsForSalePage"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickGoodsForSale(prodid) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickGoodsForSale",
            prodID: parseInt(prodid)
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("product");
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickAddCategories(productID, checkBoxSelect) {
    for (var i = 0 ; i < checkBoxSelect.length; i++) {
        if (checkBoxSelect[i].checked) {
            $.ajax({
                dataType: "xml",
                url: url,
                type: "POST",
                data: {
                    action: "addCategories",
                    categoryID: checkBoxSelect[i].value
                },
                success: function (data) {
                    if ("ok" === $(data).find("result").text().toLowerCase()) {
                    }
                },
                error: function () {
                    alert("Error while send data.");
                }
            });

        }
    }
    sendCategories(productID);
}

function sendCategories(productID) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickAddCategories",
            productID: parseInt(productID)
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickAddImage() {
    var sampleFile = document.getElementById("sampleFile").files[0];
    var formdata = new FormData();
    formdata.append("sampleFile", sampleFile);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "user", true);
    xhr.send(formdata);
    xhr.onload = function(e) {
        if (this.status == 200) {
            alert(this.responseText);
            window.location.replace(url);
        }
    };
}

function clickNewLot() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickNewLot"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

$(document).ready(function () {
    setInterval(activateButton, 1000);
});

function activateButton() {
    if (document.getElementById("sampleFile") != null) {
        var isFile = document.getElementById("sampleFile").files[0];
        if (isFile != null && isFile != "") {
            document.getElementById("addImageButton").disabled = false;
        } else {
            document.getElementById("addImageButton").disabled = true;
        }
    }
}

function checkOnHTML(input) {
    var value = input.value;
    var rep = /[-\/<>&"']/;
    if (rep.test(value)) {
        value = value.replace(rep, '');
        input.value = value;
    }
}
