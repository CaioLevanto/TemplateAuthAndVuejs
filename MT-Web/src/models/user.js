export default class User {

    constructor(firstName, 
                secondName,
                email, 
                password, 
                confirmation_password,
                phone, 
                personal, 
                address) {
                  
      this.firstName = firstName;
      this.secondName = secondName;
      this.email = email;
      this.password = password;
      this.confirmation_password = confirmation_password;
      this.phone = phone;
      this.personal = personal;
      this.address = address;
    }
    
  }