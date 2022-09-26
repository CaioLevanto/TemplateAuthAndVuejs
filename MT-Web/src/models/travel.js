export default class Travel {
    constructor(
        origin, 
        destiny,
        startDate,
        backDate,
        radioGoBack,
        adults,
        kids,
    ) {
        this.origin = origin;
        this.destiny = destiny;
        this.startDate = startDate;
        this.backDate = backDate;
        this.radioGoBack = (!radioGoBack ? true : false);
        this.adults = (adults ? 0 : 0);
        this.kids = (kids ? 0 : 0);

    }
}