package org.dcl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationConstants {
    public static final String BASEURI = "http://localhost:8080";
    public static final String BASEPATHrestr = "/api/v1/restricoes/";
    public static final String BASEPATHSim = "/api/v1/simulacoes/";
    public static final String NOME = "nome";
    public static final String CPF = "cpf";
    public static final String EMAIL = "email";
    public static final String VALOR = "valor";
    public static final String PARCELAS = "parcelas";
    public static final String SEGURO = "seguro";

    public Map simulationData(){
        Map<String, Object> params = new HashMap<>();
        params.put(NOME, "Danielly");
        params.put(CPF, "12345678916");
        params.put(EMAIL, "danielly@danielly.com");
        params.put(VALOR, 9999);
        params.put(PARCELAS, 10);
        params.put(SEGURO, true);
        return params;

    }

    public List listAllCPF(){
        List<String> listCPF = new ArrayList<>();
        listCPF.add(0, "97093236014");
        listCPF.add(1, "60094146012");
        listCPF.add(2, "84809766080");
        listCPF.add(3, "62648716050");
        listCPF.add(4, "26276298085");
        listCPF.add(5, "01317496094");
        listCPF.add(6, "55856777050");
        listCPF.add(7, "19626829001");
        listCPF.add(8, "24094592008");
        listCPF.add(9, "58063164083");
        return listCPF;
    }

}
