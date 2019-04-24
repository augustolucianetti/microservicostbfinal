package br.com.augustolucianetti.microservicostbfinal.exceptions;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>
 * Esta é a base para exceções verificadas da arquitetura do Banco Original.
 * <p>
 * Deve ser utilizado nos seguintes casos:
 * <ul>
 * <li>O chamador pode razoavelmente esperar para saber como lidar com a
 * Exceção.</li>
 * <li>A exceção é grave o suficiente para forçar o chamador a lidar com
 * isso.</li>
 * </ul>
 * </p>
 * Use RuntimeException em todos os outros casos.
 */
public abstract class AbstractException extends Exception implements ExceptionBaseData {

    private static final long serialVersionUID = -5079776245888275815L;

    private static final String BASE_INFORMATION_CODE = "error.technical.generic_checked";

    private String uniqueId;
    private String informationCode;
    private Serializable[] args;

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido como nulo, a
     * mensagem de erro é definida como nula, a exceção aninhada é definida como
     * nula e o array de argumentos <code>Serializable</code> é definido como nulo.
     */
    public AbstractException() {
        this(null, null, null, (Serializable[]) null);
    }

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido como nulo, a
     * mensagem é definida conforme o parâmetro, a exceção aninhada é definida como
     * nula e o array de argumentos <code>Serializable</code> é definido como nulo.
     *
     * @param msg Mensagem que identifica a causa raiz da exceção.
     */
    public AbstractException(final String msg) {
        this(null, msg, null, (Serializable[]) null);
    }

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido como nulo, a
     * mensagem é definida como nula, a exceção aninhada é definida conforme o
     * parâmetro e o array de argumentos <code>Serializable</code> é definido como
     * nulo.
     *
     * @param nested Exceção a ser adicionada a pilha de erros.
     *
     */
    public AbstractException(final Throwable nested) {
        this(null, null, nested, (Serializable[]) null);
    }

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido como nulo, a
     * mensagem de erro é definida conforme o parâmetro, a exceção aninhada é
     * definida conforme o parâmetro e o array de argumentos
     * <code>Serializable</code> é definido como nulo.
     *
     * @param msg    Mensagem que identifica a causa raiz da exceção.
     * @param nested Exceção a ser adicionada a pilha de erros.
     */
    public AbstractException(final String msg, final Throwable nested) {
        this(null, msg, nested, (Serializable[]) null);
    }

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido conforme o
     * parâmetro, a mensagem de erro é definida como nulo, a exceção aninhada é
     * definida conforme o parâmetro e o array de argumentos
     * <code>Serializable</code> é definido como nulo.
     *
     * @param informationCode A chave logica que defina a mensagem de erro.
     * @param msg                Mensagem que identifica a causa raiz da exceção.
     */
    public AbstractException(final String informationCode, final String msg) {
        this(informationCode, msg, (Serializable[]) null);
    }

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido conforme o
     * parâmetro, a mensagem de erro é definida conforme o parâmetro, a exceção
     * aninhada é definida como nulo e o array de argumentos
     * <code>Serializable</code> é definido é definido como nulo.
     *
     * @param informationCode A chave logica que defina a mensagem de erro.
     * @param msg             Mensagem que identifica a causa raiz da exceção.
     * @param args            As sequencias de substituicao para colocar na mensagem
     *                        de erro do usuario.
     */
    public AbstractException(final String informationCode, final String msg, final Serializable... args) {
        this(informationCode, msg, null, args);
    }

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido conforme o
     * parâmetro , a mensagem de erro é definida conforme o parâmetro, a exceção
     * aninhada é definida conforme o parâmetro e o array de argumentos
     * <code>Serializable</code> é definido como nulo.
     *
     * @param informationCode A chave logica que defina a mensagem de erro.
     * @param msg                Mensagem que identifica a causa raiz da exceção.
     * @param nested             Exceção a ser adicionada a pilha de erros.
     */
    public AbstractException(final String informationCode, final String msg, final Throwable nested) {
        this(informationCode, msg, nested, (Serializable[]) null);
    }

    /**
     * Cria uma nova instância. O código da mensagem de erro é definido conforme o
     * parâmetro , a mensagem de erro é definida conforme o parâmetro, a exceção
     * aninhada é definida conforme o parâmetro e o array de argumentos
     * <code>Serializable</code> é definido conforme o parâmetro.
     *
     * @param informationCode A chave logica que defina a mensagem de erro.
     * @param msg                Mensagem que identifica a causa raiz da exceção.
     * @param nested             Exceção a ser adicionada a pilha de erros.
     * @param args               As sequencias de substituicao para colocar na
     *                           mensagem de erro do usuario.
     */
    public AbstractException(final String informationCode, final String msg, final Throwable nested, final Serializable... args) {
        super(msg, nested);

        // Se a exceção aninhada for uma instância de ExceptionAdditionalData o
        // Unique Id desta exceção será mantido
        if (nested instanceof ExceptionBaseData) {
            this.uniqueId = ((ExceptionBaseData) nested).getUniqueId();
        } else {
            this.uniqueId = UUID.randomUUID().toString();
        }

        this.informationCode = informationCode;
        this.args = args != null ? args.clone() : null;
    }

    /**
     * Retorna a chave padrão se nenhuma foi especificada
     *
     * @return A chave padrão
     */
    protected abstract String getDefaultInformationCode();

    /**
     * Recupera um Id unico.
     *
     * @return O Id unico.
     *
     */
    public String getUniqueId() {
        return this.uniqueId;
    }

    /**
     * Retorna ou o informationCode (se nao nulo) ou a excecao padrao Chave para a
     * classe.
     *
     * @return Chave usada para procurar o erro
     *
     */
    public String getInformationCode() {
        // Use a chave atribuida quando a exececao for lancada
        String key = this.informationCode;

        // Se nao possuir chave atribuida, tente a subclasse
        if (key == null) {
            key = this.getDefaultInformationCode();
        }

        // Se na existir na subclasse, use a base padrao
        if (key == null) {
            key = AbstractException.BASE_INFORMATION_CODE;
        }

        return key;
    }

    /**
     * Retorna o array de <code>Object</code>.
     *
     * @return o array de <code>Object</code>
     */
    public Object[] getArgs() {
        Object[] objects = null;
        if (!(null == args)) {
            objects = args.clone();
        }
        return objects;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(": ").append(getUniqueId()).append(": ").append(getInformationCode()).append(": ").append(getLocalizedMessage());

        return sb.toString();
    }

}
