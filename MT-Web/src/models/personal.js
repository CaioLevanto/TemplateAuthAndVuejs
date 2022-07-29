export default class Personal {

    constructor(cpf, 
                birthday, 
                rg, 
                passportNumber, 
                driverPass, 
                driverNumber) {

      this.cpf = cpf;
      this.birthday = birthday;
      this.rg = rg;
      this.passportNumber = passportNumber;
      this.driverPass = (driverPass == null ? false : null);
      this.driverNumber = driverNumber
    }
    
  }