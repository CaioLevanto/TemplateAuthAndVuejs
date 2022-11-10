export default class Payment {

    constructor(
        title,
        dueDate,
        createdAt,
        userId,
        travelId,
        list
        

    ) {
        this.title = title;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.userId = userId;
        this.travelId = travelId;        
        this.list = list;
    }
    
}