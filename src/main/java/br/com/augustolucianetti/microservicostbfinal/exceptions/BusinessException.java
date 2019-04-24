package br.com.augustolucianetti.microservicostbfinal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * É a classe base para excecao de negocios. Ela so e usado pelo desenvolvedor.
 * A classe que a chama tem conhecimento sobre como lidar com essa excecao.
 */
@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class BusinessException extends AbstractException {

    private static final long serialVersionUID = 2872511050268931949L;

    /**
     * codigo padrao da excecao.
     */
    private static final String INFORMATION_CODE = "error.business";

    /**
     * {@inheritDoc}
     */
    public BusinessException() {
        super(null, null, null, (Serializable[]) null);
    }

    /**
     * {@inheritDoc}
     */
    public BusinessException(final String msg) {
        super(null, msg, null, (Serializable[]) null);
    }

    /**
     * {@inheritDoc}
     */
    public BusinessException(final Throwable nested) {
        super(null, null, nested, (Serializable[]) null);
    }

    /**
     * {@inheritDoc}
     */
    public BusinessException(final String msg, final Throwable nested) {
        super(null, msg, nested, (Serializable[]) null);
    }

    /**
     * {@inheritDoc}
     */
    public BusinessException(final String newInformationCode, final String msg) {
        super(newInformationCode, msg, (Serializable[]) null);
    }

    /**
     * {@inheritDoc}
     */
    public BusinessException(final String newInformationCode, final String msg, final Serializable... args) {
        super(newInformationCode, msg, null, args);
    }

    /**
     * {@inheritDoc}
     */
    public BusinessException(final String newInformationCode, final String msg, final Throwable nested) {
        super(newInformationCode, msg, nested, (Serializable[]) null);
    }

    /**
     * {@inheritDoc}
     */
    public BusinessException(final String newInformationCode, final String msg, final Throwable nested, final Serializable... args) {
        super(newInformationCode, msg, nested, args);
    }

    /**
     * Retorna a chave padra da excecao se nenhuma for definida quando a excecao e
     * lancada.
     *
     * @return codigo padrao.
     */
    @Override
    protected String getDefaultInformationCode() {
        return INFORMATION_CODE;
    }

}

