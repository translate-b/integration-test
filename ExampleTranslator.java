package de.fungate.translate.core.services.translators;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;

import de.fungate.translate.core.models.SourceLanguage;
import de.fungate.translate.core.models.Translation;
import de.fungate.translate.core.services.Curler;
import de.fungate.translate.core.services.Translator;
import fj.data.Either;

public class ExampleTranslator implements Translator {
    
    private final Curler curler;

    @Inject
    public ExampleTranslator(Curler curler) {
        this.curler = curler;
    }

    @Override
    public Set<Translation> translate(String term, SourceLanguage source) {
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
        Set<Translation> s = new HashSet<>();
        s.add(new Translation("house", "Haus"));
        return s;
    }
    
    @Override
    public String getProvider() {
        return "example provider";
    }

}
