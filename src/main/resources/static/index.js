let authShow = false;
let isAuth = false;
let userName = '';

let authDiv = '';

function onLoad() {
    // authDiv = document.getElementById('auth').innerHTML;
    // alert(authDiv);
}

function showAuth()
{
    if(!authShow)
    {
        // document.getElementById('auth').innerHTML = '
        //     <div class="card" style="width: 100%">
        //         <h2>Авторизация</h2>
        //         <div className="card-header">
        //             <form name="loginform">
        //                 <p><label>Логин: <input type="text" id="login" name="login"></label></p>
        //                 <p><label>Пароль: <input type="password" id="password" name="password"></label></p>
        //                 <p><a className="btn btn-primary" onClick="sendAuthForm()">Авторизация</a></p>
        //             </form>
        //         </div>
        //     </div>
        // ';

        document.getElementById('auth').innerHTML =
            '<div class="card" style="width: 100%">\n' +
            '        <h2>Авторизация</h2>\n' +
            '        <div class="card-header">\n' +
            '            <form name="loginform">\n' +
            '                <p><label>Логин: <input type="text" id="login" name="login"></label></p>\n' +
            '                <p><label>Пароль: <input type="password" id="password" name="password"></label></p>\n' +
            '                <p><a class="btn btn-primary" onclick="sendAuthForm()">Авторизация</a></p>\n' +
            '            </form>\n' +
            '        </div>\n' +
            '    </div>';

        authShow = true;
    }
    else
    {
        document.getElementById('auth').innerHTML = '';
        authShow = false;
    }
}

function sendAuthForm()
{
    // заполним FormData данными из формы
    let formData = new FormData(document.forms.loginform);

    // добавим ещё одно поле
    // formData.append("middle", "Иванович");

    // отправим данные
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/v1/auth");
    // xhr.send(formData);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(Object.fromEntries(formData)));

    xhr.onload = () => onAuth(xhr.response, xhr.status);
}

function onAuth(response, status) {
    // alert(response);

    if(status === 200)  // если ответ получен корректный
    {
        let json = JSON.parse(response);
        isAuth = json['auth'];

        if(isAuth)
        {
            userName = json['userName'];
            alert('Вы успешно авторизованы!')
        }
        else
        {
            userName = '';
            alert('Авторизация не удалась!')
        }
        // alert(json['auth'] + ' ' + json['userName']);
    }
}