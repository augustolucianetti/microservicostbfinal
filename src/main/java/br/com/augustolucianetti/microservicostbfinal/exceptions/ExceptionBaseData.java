package br.com.augustolucianetti.microservicostbfinal.exceptions;

public interface ExceptionBaseData {

    /**
     * Retorna o id único da exceção.
     *
     * @return O id único
     */
    String getUniqueId();

    /**
     * Retorna o infoCode
     *
     * @return O infoCode
     */
    String getInformationCode();

    /**
     * Retorna um array de objetos associados a exceção
     *
     * @return Array de <code>Object</code>
     */
    Object[] getArgs();
}
