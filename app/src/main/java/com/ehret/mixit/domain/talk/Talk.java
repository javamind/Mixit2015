/*
 * Copyright 2015 Guillaume EHRET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ehret.mixit.domain.talk;

import com.ehret.mixit.domain.Salle;

/**
 * Représente aussi bien un atelier qu'une conf
 */
public class Talk extends Conference<Talk> {
    private String format;
    private String level;




    public String getFormat() {
        return format==null ? "Talk" : format;
    }

    public Talk setFormat(String format) {
        this.format = format;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Talk setEventSpecial(){
        setLevel("");
        setDescription("");
        setLanguage("");
        setFormat("Special");
        setRoom(Salle.INCONNU.getNom());
        return this;
    }
}
