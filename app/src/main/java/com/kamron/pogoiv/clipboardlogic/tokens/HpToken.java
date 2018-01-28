package com.kamron.pogoiv.clipboardlogic.tokens;

import android.content.Context;

import com.kamron.pogoiv.R;
import com.kamron.pogoiv.clipboardlogic.ClipboardToken;
import com.kamron.pogoiv.scanlogic.IVScanResult;
import com.kamron.pogoiv.scanlogic.PokeInfoCalculator;
import com.kamron.pogoiv.scanlogic.Pokemon;

/**
 * Created by Johan on 2016-09-26.
 * A token representing the hp of the pokemon.
 */

public class HpToken extends ClipboardToken {
    private boolean currentLevel; //whether the user wants to know hp for a level 40 pokemon, or the current level.

    /**
     * Create a clipboard token.
     * The boolean in the constructor can be set to false if pokemon evolution is not applicable.
     *
     * @param maxEv true if the token should change its logic to pretending the pokemon is fully evolved.
     */
    public HpToken(boolean maxEv, boolean currentLevel) {
        super(maxEv);
        this.currentLevel = currentLevel;
    }

    @Override
    public int getMaxLength() {
        return 3;
    }

    @Override
    public String getValue(IVScanResult ivScanResult, PokeInfoCalculator pokeInfoCalculator) {
        Pokemon poke = getRightPokemon(ivScanResult.pokemon, pokeInfoCalculator);
        double level = currentLevel ? ivScanResult.estimatedPokemonLevel.min : 40;
        int hp = pokeInfoCalculator.getHPAtLevel(ivScanResult, level, poke);
        return String.valueOf(hp);
    }

    @Override
    public String getStringRepresentation() {
        return super.getStringRepresentation() + String.valueOf(currentLevel);
    }

    @Override
    public String getPreview() {
        int hp = currentLevel ? 30 : 70;
        if (maxEv) {
            hp = hp * 2;
        }
        return String.valueOf(hp);
    }

    @Override
    public String getTokenName(Context context) {
        return currentLevel ? context.getString(R.string.hp) : context.getString(R.string.hp) + "+";
    }

    @Override
    public String getLongDescription(Context context) {
        if (currentLevel) {
            return context.getString(R.string.token_msg_hpToken_msg1);
        }
        return context.getString(R.string.token_msg_hpToken_msg2);
    }

    @Override
    public Category getCategory() {
        return Category.BASIC_STATS;
    }

    @Override
    public boolean changesOnEvolutionMax() {
        return true;
    }
}
