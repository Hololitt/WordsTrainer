package com.hololitt.SpringBootProject.services;

import com.hololitt.SpringBootProject.models.LanguageCard;
import com.hololitt.SpringBootProject.models.LanguageCardCreationForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LanguageCardCacheService {
    private final LanguageCardService languageCardService;
    private final UserService userService;
    private final List<LanguageCard> createdLanguageCards = new ArrayList<>();
    public LanguageCardCacheService(LanguageCardService languageCardService, UserService userService){
        this.languageCardService = languageCardService;
        this.userService = userService;
    }
    private final Map<Long, List<LanguageCard>> cachedLanguageCardsByUser = new HashMap<>();
    private List<LanguageCard> lastLearnedLanguageCards = new ArrayList<>();
    public void deleteCreatedLanguageCards(){
        if(!createdLanguageCards.isEmpty()){
            createdLanguageCards.clear();
        }
    }
    public void addCreatedLanguageCard(LanguageCardCreationForm languageCardCreationForm, long userId){
        String word = languageCardCreationForm.getWord();
        String translation = languageCardCreationForm.getTranslation();
        createdLanguageCards.add(new LanguageCard(word, translation, userId));
    }
    public List<LanguageCard> getCreatedLanguageCards(){
        return createdLanguageCards;
    }
    public List<LanguageCard> getLanguageCardsByUser() {
        long userId = userService.getUserId();
        if (cachedLanguageCardsByUser.containsKey(userId)) {
            return cachedLanguageCardsByUser.get(userId);
        } else {
            List<LanguageCard> languageCards = languageCardService.getLanguageCardsByUserId(userId);
            cachedLanguageCardsByUser.put(userId, languageCards);
            return languageCards;
        }
    }
    public void updateLanguageCardsForUser(long userId) {
        List<LanguageCard> languageCards = languageCardService.getLanguageCardsByUserId(userId);
        cachedLanguageCardsByUser.put(userId, languageCards);
    }
    public void setLastLearnedLanguageCards(List<LanguageCard> lastLearnedLanguageCards){
        this.lastLearnedLanguageCards = lastLearnedLanguageCards;
    }
    public List<LanguageCard> getLastLearnedLanguageCards(){
        return lastLearnedLanguageCards;
    }
}