package com.dao.mobile.artifact.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Manipulação de valores strings.
 *
 * @author Diogo Oliveira.
 */
public class Strings
{
    public static String EMPTY = "u\0200";

    /**
     * Remove acentuação.
     *
     * @param value "string atual".
     *
     * @return (string sem acentuação)
     */
    public static String accents(String value)
    {
        if(value == null)
        {
            return null;
        }
        else
        {
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String decomposed = Normalizer.normalize(value, Normalizer.Form.NFD);
            return pattern.matcher(decomposed).replaceAll("");
        }
    }

    public static int matcherCount(String regex, String input)
    {
        int count = 0;

        if(!isEmpty(regex) && !isEmpty(input))
        {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            while(matcher.find())
            {
                count++;
            }
        }

        return count;
    }

    /**
     * Verifica se o "TextView" não contém texto.
     *
     * @param textView "textView atual".
     *
     * @return (true se vazio)
     */
    public static boolean isEmpty(@NonNull TextView textView)
    {
        /* analyze */
        return ((textView.getText() == null) || isEmpty(textView.getText().toString()));
    }

    /**
     * Verifica se o "EditText" não contém texto.
     *
     * @param editText "editText atual".
     *
     * @return (true se vazio)
     */
    public static boolean isEmpty(@NonNull EditText editText)
    {
        /* analyze */
        return ((editText.getText() == null) || isEmpty(editText.getText().toString()));
    }

    /**
     * Verifica se o texto é vazio.
     *
     * @param text "texto atual".
     *
     * @return (true se texto vazio)
     */
    public static boolean isEmpty(@Nullable CharSequence text)
    {
        /* analyze */
        return (text == null || text.toString().trim().length() == 0);
    }

    /**
     * Verifica se o texto é vazio.
     *
     * @param text "texto atual".
     *
     * @return (true se texto vazio)
     */
    public static boolean isEmpty(@Nullable String text)
    {
        /* analyze */
        return (text == null || text.trim().length() == 0);
    }

    /**
     * Verifica se o "EditText" não contém texto ou espaço em branco.
     *
     * @param editText "editText atual".
     *
     * @return (true se texto vazio)
     */
    public static boolean isEmptyOrWhite(@NonNull EditText editText)
    {
        return (editText.getText() == null || isEmptyOrWhite(editText.getText().toString()));
    }

    /**
     * Verifica se o "EditText" não contém texto ou espaço em branco.
     *
     * @param text "texto atual".
     *
     * @return (true se texto vazio)
     */
    public static boolean isEmptyOrWhite(@Nullable String text)
    {
        /* analyze */
        return (text == null || text.length() == 0);
    }

    /**
     * Compara dois valores não nulo, se coincidem.
     *
     * @param text1 "texto 1 atual".
     * @param text2 "texto 2 atual".
     *
     * @return (true se textos coincidem)
     */
    public static boolean equals(@NonNull String text1, @NonNull String text2)
    {
        /* analyze */
        return (text1.trim().equals(text2.trim()));
    }

    /**
     * Compara dois valores não nulo, se coincidem ignorando "case sensitive".
     *
     * @param text1 "texto 1 atual".
     * @param text2 "texto 2 atual".
     *
     * @return (true se textos coincidem)
     */
    public static boolean equalsIgnoreCase(@NonNull String text1, @NonNull String text2)
    {
        /* analyze */
        return (text1.trim().equalsIgnoreCase(text2.trim()));
    }

    /**
     * Reduz um texto para um tamanho pretendido.
     *
     * @param text   "texto atual".
     * @param length "comprimento maximo".
     *
     * @return (text menor ou igual ao comprimento fornecido)
     */
    public static String proportionalSplit(String text, int length)
    {
        StringBuilder result = new StringBuilder();

        if(((text != null) && (!TextUtils.isEmpty(text.trim()))))
        {
            String[] array = text.split("\u0020");

            if(array.length > 1)
            {
                for(String str : array)
                {
                    if((result.toString().trim().concat("\u0020").concat(str).length() <= length))
                    {
                        result.append("\u0020").append(str);
                    }
                    else
                    {
                        break;
                    }
                }
            }
            else
            {
                result.append(array[0].substring(length));
            }
        }

        return result.toString().trim();
    }

    /**
     * Destaca parte do texto.
     *
     * @param match "parte do texto a ser destacado".
     * @param text  "texto original do qual o match faz parte".
     *
     * @return (texto destacado)
     */
    public static SpannableStringBuilder highlight(CharSequence match, String text)
    {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        if(!isEmpty(text) && !isEmpty(match))
        {
            String matchLower = accents(match.toString().toLowerCase()).trim();
            String textLower = accents(text.toLowerCase());

            if(textLower.contains(matchLower))
            {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(158, 158, 158));
                StyleSpan styleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
                Pattern pattern = Pattern.compile("[^" + matchLower + "]*" + matchLower);
                Matcher matcher = pattern.matcher(textLower);

                while(matcher.find())
                {
                    int start = textLower.indexOf(matchLower);
                    int end = (start + matchLower.length());

                    builder.setSpan(colorSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    builder.setSpan(styleSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }

        return builder;
    }

    /**
     * Destaca parte do texto.
     *
     * @param match "parte do texto a ser destacado".
     * @param text  "texto original do qual o match faz parte".
     * @param color "cor para o texto em destaque".
     *
     * @return (texto destacado)
     */
    public static SpannableStringBuilder highlight(CharSequence match, String text, @ColorRes int color)
    {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        if(!isEmpty(text) && !isEmpty(match))
        {
            String matchLower = accents(match.toString().toLowerCase()).trim();
            String textLower = accents(text.toLowerCase());

            if(textLower.contains(matchLower))
            {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Colors.get(color));
                StyleSpan styleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
                Pattern pattern = Pattern.compile("[^" + matchLower + "]*" + matchLower);
                Matcher matcher = pattern.matcher(textLower);

                while(matcher.find())
                {
                    int start = textLower.indexOf(matchLower);
                    int end = (start + matchLower.length());

                    builder.setSpan(colorSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    builder.setSpan(styleSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }

        return builder;
    }

    /**
     * Destaca parte do texto usando case sensitive.
     *
     * @param match "parte do texto a ser destacado".
     * @param text  "texto original do qual o match faz parte".
     *
     * @return (texto destacado)
     */
    public static SpannableStringBuilder highlightSensitive(CharSequence match, String text)
    {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        if(!isEmpty(text) && !isEmpty(match))
        {
            if(text.contains(match))
            {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(158, 158, 158));
                StyleSpan styleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
                Pattern pattern = Pattern.compile("[^" + match + "]*" + match);
                Matcher matcher = pattern.matcher(text);

                while(matcher.find())
                {
                    int start = text.indexOf(match.toString());
                    int end = (start + match.length());

                    builder.setSpan(colorSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    builder.setSpan(styleSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }

        return builder;
    }

    /**
     * Destaca palavras ou parte do texto de acordo com o estilo pretendido.
     *
     * @param text "texto atual".
     * @param list "lista de estilos".
     *
     * @return (texto destacado)
     */
    public static SpannableStringBuilder spannable(String text, ListSpan list)
    {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        if(!isEmpty(text) && (list != null))
        {
            List<String> resources = Arrays.asList(text.split("#\\$#"));

            if(resources.size() == list.size())
            {
                int count;
                Iterator<String> strings = resources.iterator();

                for(Span span : list.get())
                {
                    if(span.isSpan)
                    {
                        if(span.isImage())
                        {
                            builder.setSpan(span.getImage(), (builder.length() - 1), builder.length(), 0);
                        }
                        else
                        {
                            String str = strings.next();
                            builder.append(str);

                            count = (builder.length() - str.length());

                            if(span.isStyle())
                            {
                                builder.setSpan(span.getStyle(), count, (count + str.length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                            if(span.isColor())
                            {
                                builder.setSpan(span.getColor(), count, (count + str.length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                            if(span.isSize())
                            {
                                builder.setSpan(span.getSize(), count, (count + str.length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }
                    else
                    {
                        builder.append(strings.next());
                    }
                }
            }
        }

        return builder;
    }

    /**
     * Lista de estilo para destacar texto.
     */
    public static class ListSpan
    {
        private final List<Span> list;

        public ListSpan(int capacity)
        {
            list = new ArrayList<>(capacity);
        }

        public List<Span> get()
        {
            return list;
        }

        public ListSpan add(Span span)
        {
            list.add(span);
            return this;
        }

        public int size()
        {
            int count = 0;

            for(Span span : list)
            {
                if(!span.isImage())
                {
                    ++count;
                }
            }

            return count;
        }
    }

    /**
     * Estilos para destacar texto.
     */
    public static class Span
    {
        private final boolean isSpan;
        private StyleSpan styleSpan;
        private ImageSpan imageSpan;
        private AbsoluteSizeSpan absoluteSizeSpan;
        private ForegroundColorSpan foregroundColorSpan;

        private Span(boolean span)
        {
            this.isSpan = span;
        }

        public static Span build()
        {
            return new Span(true);
        }

        public static Span none()
        {
            return new Span(false);
        }

        public Span style(int style)
        {
            styleSpan = new StyleSpan(style);
            return this;
        }

        public Span color(int color)
        {
            foregroundColorSpan = new ForegroundColorSpan(color);
            return this;
        }

        public Span size(int size)
        {
            absoluteSizeSpan = new AbsoluteSizeSpan(size, true);
            return this;
        }

        public Span image(Context context, Bitmap bitmap)
        {
            imageSpan = new ImageSpan(context, bitmap);
            return this;
        }

        public Span image(Context context, @DrawableRes int drawable)
        {
            imageSpan = new ImageSpan(context, drawable);
            return this;
        }

        public ImageSpan getImage()
        {
            return imageSpan;
        }

        public StyleSpan getStyle()
        {
            return styleSpan;
        }

        public AbsoluteSizeSpan getSize()
        {
            return absoluteSizeSpan;
        }

        public ForegroundColorSpan getColor()
        {
            return foregroundColorSpan;
        }

        public boolean isStyle()
        {
            return (styleSpan != null);
        }

        public boolean isColor()
        {
            return (foregroundColorSpan != null);
        }

        public boolean isSize()
        {
            return (absoluteSizeSpan != null);
        }

        public boolean isImage()
        {
            return (imageSpan != null);
        }
    }
}
