package br.com.mucatour.model.travel.enums;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum StatusTravel {
    PENDENTE("Solicitação Pendente", "red"),
    AGUARDANDO_CONFIRMACAO("Aguardando Confirmação", "yellow"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento", "red"),
    PAGAMENTO_CONFIRMADO("Pagamento Confirmado", "green"),
    EM_ANDAMENTO("Em Andamento", "blue"),
    RE_AGENDAR("Re Agendar", "red"),
    CANCELADO("Cancelado", "black");

    private String name;
    private String color;

    public static String getEnumByString(String code){
        for(StatusTravel e : StatusTravel.values()){
            if(e.name.equals(code)) return e.name();
        }
        return null;
    }

    public static String getListEnum() {
        JSONArray list = new JSONArray();
        JSONObject obj;
        for(StatusTravel e : StatusTravel.values()){
            obj = new JSONObject();
            obj.put("name", e.name);
            obj.put("color", e.color);
            list.put(obj);
        }   
        return list.toString();
    }
}
