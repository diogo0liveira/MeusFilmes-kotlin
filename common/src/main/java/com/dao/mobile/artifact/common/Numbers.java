package com.dao.mobile.artifact.common;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Manipulação de valores numericos.
 *
 * @author Diogo Oliveira.
 */
public class Numbers
{
    private final static String TAG = "Numbers";

    private final static NumberFormat numberFormat = NumberFormat.getInstance();

    public Numbers()
    {
        numberFormat.setMaximumFractionDigits(3);
    }

    /**
     * Recupera o valor de BigDecimal em Double.
     *
     * @param value "valor atual"
     *
     * @return (valor numérico do tipo Doble)
     */
    public static Double valueOf(BigDecimal value)
    {
        return ((value != null) ? value.doubleValue() : null);
    }

    /**
     * Recupera o valor de Double em BigDecimal.
     *
     * @param value "valor atual"
     *
     * @return (valor numérico do tipo BigDecimal)
     */
    public static BigDecimal valueOf(Double value)
    {
        return ((value != null) ? new BigDecimal(value.toString()) : null);
    }

    /**
     * Recupera o valor de String em BigDecimal.
     *
     * @param value "valor atual"
     *
     * @return (valor numérico do tipo BigDecimal)
     */
    public static BigDecimal valueOf(String value)
    {
        return (Strings.isEmpty(value) ? null : new BigDecimal(parse(value)));
    }

    /**
     * Converte string para numérico.
     *
     * @param value "valor em string"
     *
     * @return (valor numérico do tipo Doble)
     */
    public static Double parse(String value)
    {
        if(!Strings.isEmpty(value))
        {
            value = value.replaceAll("\\.", "");
            value = value.replace(",", ".");
            return (value.matches("^-?(0|[1-9]\\d*)(\\.\\d+)?$") ? Double.valueOf(value) : null);
        }

        return null;
    }

    /**
     * Formata o valor de acordo com o tipo.
     *
     * @param value      "valor atual".
     * @param isFraction "se é para formatado como fração".
     *
     * @return (valor formatado)
     */
    public static String format(Double value, boolean isFraction)
    {
        if(value != null)
        {
            numberFormat.setGroupingUsed(true);
            numberFormat.setMinimumFractionDigits((isFraction ? 3 : 0));
            return numberFormat.format(value);
        }

        return null;
    }

    /**
     * Verifica que o valor contido no objeto é composto apenas de numeros inteiros
     *
     * @param value "valor atual".
     *
     * @return (true se objeto for um inteiro)
     */
    public static boolean isInteger(Object value)
    {
        return ((value != null) && (value.toString().matches("\\d+")));
    }

    /**
     * Se o valor não representa um valor somatório.
     * <p>
     * <code>
     * return ((value == null) || (value <= 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor igual a zero)
     */
    public static boolean isEmpty(Float value)
    {
        return ((value == null) || (value <= 0));
    }

    /**
     * Se o valor não representa um valor somatório.
     * <p>
     * <code>
     * return ((value == null) || (value <= 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor igual a zero)
     */
    public static boolean isEmpty(Double value)
    {
        return ((value == null) || (value <= 0));
    }

    /**
     * Se o valor não representa um valor somatório.
     * <p>
     * <code>
     * return ((value == null) || (value <= 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor igual a zero)
     */
    public static boolean isEmpty(Long value)
    {
        return ((value == null) || (value <= 0));
    }

    /**
     * Se o valor não representa um valor somatório.
     * <p>
     * <code>
     * return ((value == null) || (value <= 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor igual a zero)
     */
    public static boolean isEmpty(Integer value)
    {
        return ((value == null) || (value <= 0));
    }

    /**
     * Se o valor não representa um valor somatório.
     * <p>
     * <code>
     * return ((value == null) || (value.compareTo(BigDecimal.ONE) <= 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor igual a zero)
     */
    public static boolean isEmpty(BigDecimal value)
    {
        return ((value == null) || (value.compareTo(BigDecimal.ONE) <= 0));
    }

    /**
     * Se o valor representa um valor nulo ou negativo.
     * <p>
     * <code>
     * return ((value == null) || (value < 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor que zero)
     */
    public static boolean isNegative(Float value)
    {
        return ((value == null) || (value < 0));
    }

    /**
     * Se o valor representa um valor nulo ou negativo.
     * <p>
     * <code>
     * return ((value == null) || (value < 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor que zero)
     */
    public static boolean isNegative(Integer value)
    {
        return ((value == null) || (value < 0));
    }

    /**
     * Se o valor representa um valor nulo ou negativo.
     * <p>
     * <code>
     * return ((value == null) || (value < 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor que zero)
     */
    public static boolean isNegative(Long value)
    {
        return ((value == null) || (value < 0));
    }

    /**
     * Se o valor representa um valor nulo ou negativo.
     * <p>
     * <code>
     * return ((value == null) || (value < 0));
     * </code>
     *
     * @param value "valor atual".
     *
     * @return (true se diferente igual a nulo ou menor que zero)
     */
    public static boolean isNegative(Double value)
    {
        return ((value == null) || (value < 0));
    }
}
