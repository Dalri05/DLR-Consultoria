package com.joaod.DLRConsultoria.service;

public class MontarCorpoEmailService {

    public static String montarCorpotEmailContrato(
            String nomeEmpresa,
            String nomeConsultor,
            int numerosContrato
    ) {
        StringBuilder corpoEmail = new StringBuilder();
        corpoEmail.append("<html>");
        corpoEmail.append("<body style='font-family: Arial, sans-serif; line-height: 1.6;'>");
        corpoEmail.append("<h1 style='color: #007bff;'>Seja bem-vindo à DLR Consultoria, " + nomeEmpresa + "!</h1>");
        corpoEmail.append("<p>Obrigado por se inscrever no nosso serviço.</p>");
        if (numerosContrato > 0)
            corpoEmail.append("<p>Este é o nosso " + numerosContrato + "º contrato fechado. Ficamos felizes que estão gostando de nosso serviços.</p>");
        corpoEmail.append("<p>Seu consultor responsável pelos nossos serviços será o " + nomeConsultor + ".</p>");
        corpoEmail.append("<p style='margin-top: 20px; color: #555;'>Atenciosamente, <br> DLR Consultoria</p>");
        corpoEmail.append("</body>");
        corpoEmail.append("</html>");

        return corpoEmail.toString();
    }
}
