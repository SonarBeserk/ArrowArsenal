package me.sonarbeserk.arrowarsenal.arrows;

import me.sonarbeserk.arrowarsenal.enums.ArrowState;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***********************************************************************************************************************
 *
 * ArrowArsenal - Bukkit plugin to provide a system to organize special arrows
 * ===========================================================================
 *
 * Copyright (C) 2013, 2014 by SonarBeserk
 * http://dev.bukkit.org/bukkit-plugins/arrowarsenal/
 *
 ***********************************************************************************************************************
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ***********************************************************************************************************************/
public class ExampleArrow extends SArrow {

    ArrowState state = null;

    @Override
    public String getDisplayName() {
        return "&2Example Arrow";
    }

    @Override
    public String getInternalName() {
        return "arrow_example";
    }

    @Override
    public String getDescription() {
        return "Fairly Useless arrow for example purposes";
    }

    @Override
    public List<String> getAuthors() {

        List<String> authors = new ArrayList<String>();

        authors.add("SonarBeserk");

        return authors;
    }

    @Override
    public String getMainPermission() {
        return "arrowarsenal.arrows.example";
    }

    @Override
    public Map<String, String> getExtraPermissions() {

        Map<String, String> perms = new HashMap<String, String>();

        perms.put("arrowarsenal.arrow.example.exampleperm", "Example permission listing for player use");

        return perms;
    }

    @Override
    public boolean canBuy() {

        return false;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public ArrowState getState() {

        return state;
    }

    @Override
    public void launch(ProjectileLaunchEvent event) {

        state = ArrowState.PROGRESSING;
    }

    @Override
    public void hit(ProjectileHitEvent event) {

        state = ArrowState.SUCCEEDED;
    }

    @Override
    public void hitEntity(EntityDamageByEntityEvent event) {

        state = ArrowState.SUCCEEDED;
    }

    @Override
    public void onEnable() {

        //fancy code to run on being enabled
    }

    @Override
    public void onDisable() {

        //fancy code to run on being disabled
    }
}
