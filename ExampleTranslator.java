package core.services.translators;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import core.models.SourceLanguage;
import core.models.Translation;
import core.services.Curler;
import core.services.TranslationFactory;
import core.services.Translator;
import fj.data.Either;

public class ExampleTranslator implements Translator {
    
    private final TranslationFactory translationFactory;
    private final Curler curler;

    @Inject
    public ExampleTranslator(TranslationFactory translationFactory, Curler curler) {
        this.translationFactory = translationFactory;
        this.curler = curler;
    }

    @Override
    public Iterable<Translation> translate(String term, SourceLanguage source) {
        Either<String, Exception> content = curler.get("http://www.google.de/");
        if (content.isLeft()) { 
            // The left type is present, so the GET request was successful and
            // we can access the inner string value
            //System.out.println(content.left().value());
        } else {
            // Get request failed and the right type is present. We can lookup the Exception
            //content.right().value().printStackTrace();
        }
        // If you have a sensible default and don't want to inspect the 
        // right (usually exception) value any further, use something like
        String contentString = content.left().orValue("");
        // which returns the empty string, if there is no value present in content.
        
        
        // parse contentString with Jsoup
        List<Translation> l = new ArrayList<Translation>();
        l.add(translationFactory.makeTranslation("house", "haus", getProvider(), "noun"));
        return l;
    }
    
    @Override
    public String getProvider() {
        return "example provider";
    }

}
