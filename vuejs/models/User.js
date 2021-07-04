export class User{
    constructor(obj={}) {
        this.id = obj.id;
        this.username = obj.username;
        this.role = obj.role;
        this.image = obj.image;
        this.isAuth = false;

        this.setIsAuthenticated();
    }

    setIsAuthenticated() {
        if(Number.isInteger(this.id) && this.id){
            this.isAuth = true;
        }
    }
}