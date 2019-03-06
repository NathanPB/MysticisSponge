package dev.nathanpb.mysticis.data

import com.google.common.reflect.TypeToken
import org.spongepowered.api.data.DataContainer
import org.spongepowered.api.data.DataQuery
import org.spongepowered.api.data.DataSerializable
import org.spongepowered.api.data.key.Key
import org.spongepowered.api.data.value.mutable.Value
import org.spongepowered.api.util.TypeTokens

/*
Copyright 2019 Nathan Bombana
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

/**
 *
 * @author NathanPB
 * @since 1.0.0
 */
class ManaData(
    var air   : Int = 0,
    var fire  : Int = 0,
    var water : Int = 0,
    var ice   : Int = 0,
    var nature: Int = 0,
    var magic : Int = 0,
    var dark  : Int = 0
) : DataSerializable {

    companion object {
        val MANA_KEY = Key.builder()
            .type(object: TypeToken<Value<ManaData>>(){})
            .query(DataQuery.of("mana"))
            .id("mana")
            .name("Mana")
            .build()

        val AFFINITY_KEY = Key.builder()
            .type(object: TypeToken<Value<ManaData>>(){})
            .query(DataQuery.of("magic_affinity"))
            .id("magic_affinity")
            .name("Magic Affinity")
            .build()

        val LEVEL_KEY = Key.builder()
            .type(TypeTokens.INTEGER_VALUE_TOKEN)
            .query(DataQuery.of("magic_level"))
            .id("magic_level")
            .name("Magic Level")
            .build()

        val AIR_KEY    = Key.builder().type(TypeTokens.INTEGER_VALUE_TOKEN).query(DataQuery.of("air")).id("air").name("Air").build()
        val FIRE_KEY   = Key.builder().type(TypeTokens.INTEGER_VALUE_TOKEN).query(DataQuery.of("fire")).id("fire").name("Fire").build()
        val WATER_KEY  = Key.builder().type(TypeTokens.INTEGER_VALUE_TOKEN).query(DataQuery.of("water")).id("water").name("Water").build()
        val ICE_KEY    = Key.builder().type(TypeTokens.INTEGER_VALUE_TOKEN).query(DataQuery.of("ice")).id("ice").name("Ice").build()
        val NATURE_KEY = Key.builder().type(TypeTokens.INTEGER_VALUE_TOKEN).query(DataQuery.of("nature")).id("nature").name("Nature").build()
        val MAGIC_KEY  = Key.builder().type(TypeTokens.INTEGER_VALUE_TOKEN).query(DataQuery.of("magic")).id("magic").name("Magic").build()
        val DARK_KEY   = Key.builder().type(TypeTokens.INTEGER_VALUE_TOKEN).query(DataQuery.of("dark")).id("dark").name("Dark").build()
    }


    fun sum(data: ManaData)= this.also {
        air    += data.air
        fire   += data.fire
        water  += data.water
        ice    += data.ice
        nature += data.nature
        magic  += data.magic
        dark   += data.dark
    }

    fun dec(data: ManaData) = this.also {
        air    -= data.air
        fire   -= data.fire
        water  -= data.water
        ice    -= data.ice
        nature -= data.nature
        magic  -= data.magic
        dark   -= data.dark
    }

    fun hasNegatives() = arrayOf(air, fire, water, ice, nature, magic, dark).any { it < 0 }

    override fun toContainer() = DataContainer.createNew().also {
        it[AIR_KEY]    = air
        it[FIRE_KEY]   = fire
        it[WATER_KEY]  = water
        it[ICE_KEY]    = ice
        it[NATURE_KEY] = nature
        it[MAGIC_KEY]  = magic
        it[DARK_KEY]   = dark
    }

    override fun getContentVersion() = 1
}