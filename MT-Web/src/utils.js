function validaVazio(field) 
{
    return (field != null && field > 0 && field != "" && field);
}

function tratarErroCep(error) {
    let message = "";
    
    error.response.data.errors.forEach(err => {
        if (!err.message.includes("Erro ao se conectar com o serviço")) {
            message = err.message;
            return;
        }
    });
    return (error.response.status + " - " + message);
}

export { validaVazio, tratarErroCep };