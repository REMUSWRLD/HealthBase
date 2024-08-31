<script>
import loginService from '../services/login-service';
export default {
    data() {
        return {
            newAccount: {
                username: '',
                email: '',
                password: ''
            },
            passwordConfirm: '',
            registerError: null,

            username: '',
            password: '',
            rememberMe: false,
            loginError: null
        }
    },
    methods: {
        login() {
            loginService.login(this.username, this.password, this.rememberMe).then(loginResult => {
                if (!loginResult) {
                    this.loginError = 'Invalid username or password';
                } else {
                    this.$router.push('/');
                }
            });
        },
        register() {
        if (this.newAccount.password !== this.passwordConfirm) {
            this.registerError = 'Passwords do not match';
            return;
        }
        loginService.register(this.newAccount).then(registerResult => {
                console.log(registerResult);
                this.$router.push('/');
                this.passwordConfirm = '';
                this.username = this.newAccount.username;
                this.password = this.newAccount.password;
                this.newAccount = {
                    username: '',
                    email: '',
                    password: ''
                }
            }).catch(error => {
                this.registerError = 'Error registering account';
            });
        }
    }
}
</script>

<template>
    <body>
        
        <form @submit.prevent="login" id="login-container">
            <h1>Login</h1>
            <p class="login-error" v-if="loginError">{{ loginError }}</p>
            <fieldset>
                <label>
                    <span> Username </span>
                    <input type="text" v-model="username" required>
                </label>
                <label>
                    <span>Password </span>
                    <input type="password" v-model="password" required>
                </label>
                <label id="remember-me">
                    <input type="checkbox" v-model="rememberMe"/>
                    <span>Remember me</span>
                </label>
                <button type="submit">Login</button>
            </fieldset>
        </form>
        
        <form @submit.prevent="register" id="register-container">
            <h1>Register</h1>
            <p class="register-error" v-if="registerError">{{ registerError }}</p>                
            <fieldset>
                <label>
                    <span>Username </span>
                    <input type="text" v-model="newAccount.username" required>
                </label>
                <label>
                    <span>Email </span>
                    <input type="text" v-model="newAccount.email" required>
                </label>
                <label>
                    <span>Password </span>
                    <input type="password" v-model="newAccount.password" required>
                </label>
                <label>
                    <span>Confirm Password </span>
                    <input type="password" v-model="passwordConfirm" required>
                </label>
                <button type="submit">Register</button>
            </fieldset>
        </form>


    </body>
</template>

<style scoped>
    body {
        background-image: url('../assets/img/background.jpg');
        background-size: 90%; 
        background-position: left;
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-rows: 1fr 1fr;
        height: 100vh;
        overflow: hidden;
    }

    #login-container, #register-container {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    #login-container {
        grid-column: 3;
        grid-row: 1/2;
        display: flex;
        flex-direction: column;
        padding-top: 10px;
        background-color: #d5ffca;
    }

    #register-container {
        background-color: white;
        grid-column: 3;
        grid-row: 3;
    }

    label {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: flex-start;
        gap: 8px;
        margin-top: 10px;
    }

    fieldset {
        margin-bottom: 10px;
        border: none;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        color: #1e1e1e;
    }

    input {
        border-color: #c9c9c9;
        border-radius: 50px;
        padding: 10px;
        color: black;
    }

    button {
        width: 250px;
        border-radius: 50px;
        margin-bottom: 5px;
        padding: 10px;
        margin-top: 10px;
        align-items: center;
        justify-content: center;
    }

    button, input{
        cursor: pointer;
    }

    button:hover {
        background-color: #383838;
    }
    input:hover {  
        background-color: rgb(235, 235, 235);
    }

</style>