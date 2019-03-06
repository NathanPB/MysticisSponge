package dev.nathanpb.mysticis.listener

import dev.nathanpb.mysticis.Mysticis
import dev.nathanpb.mysticis.data.ManaData
import org.spongepowered.api.Game
import org.spongepowered.api.block.BlockType
import org.spongepowered.api.block.BlockTypes
import org.spongepowered.api.data.key.Keys
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.block.TickBlockEvent
import org.spongepowered.api.scheduler.Task
import org.spongepowered.api.world.biome.BiomeTypes
import kotlin.random.Random

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
class ManaListener {

    companion object {
        /**
         * Task that runs every tick passively updating updating player data.
         */
        val taskPassiveChangePlayerData = Task.builder().execute { _ ->
            val randomizer = Random.nextInt(0, 1000) //Generating random values 20*playerCount times a second would be bad
            Mysticis.instance.game.server.onlinePlayers.forEach {
                val mana       = it[ManaData.MANA_KEY].orElse(ManaData())
                val affinity   = it[ManaData.AFFINITY_KEY].orElse(ManaData())
                val level      = it[ManaData.LEVEL_KEY].orElse(0)
                
                passiveChangeMana(mana, affinity, level, randomizer)
                playerLocationChangeAffinity(it, affinity, level, randomizer)

                //If the player is on fire, his affinity should increase. Not applicable when its on nether.
                if(randomizer > level && it.location.biome != BiomeTypes.HELL && it[Keys.FIRE_TICKS].orElse(0) > 0){
                    mana.fire++
                }

                it.offer(ManaData.MANA_KEY, mana)
                it.offer(ManaData.AFFINITY_KEY, affinity)
            }
        }.intervalTicks(1).async()

        /**
         * Changes player's mana according to its affinity.
         *
         * @param[mana] mana data holder.
         * @param[affinity] affinity data holder.
         * @param[level] player magic level.
         * @param[randomizer] random integer from 0 to 1000
         */
        private fun passiveChangeMana(mana: ManaData, affinity: ManaData, level: Int, randomizer: Int){
            if (affinity.air < 50) {
                if (randomizer <= -(affinity.air - 50) * 10) {
                    mana.air--
                }
            } else {
                if (randomizer <= level / 50 * affinity.air + 1) {
                    mana.air++
                }
            }

            if (affinity.fire < 50) {
                if (randomizer <= -(affinity.fire - 50) * 10) {
                    mana.fire--
                }
            } else {
                if (randomizer <= level / 50 * affinity.fire + 1) {
                    mana.fire++
                }
            }

            if (affinity.water < 50) {
                if (randomizer <= -(affinity.water - 50) * 10) {
                    mana.water--
                }
            } else {
                if (randomizer <= level / 50 * affinity.water + 1) {
                    mana.water++
                }
            }

            if (affinity.ice < 50) {
                if (randomizer <= -(affinity.ice - 50) * 10) {
                    mana.ice--
                }
            } else {
                if (randomizer <= level / 50 * affinity.ice + 1) {
                    mana.ice++
                }
            }

            if (affinity.nature < 50) {
                if (randomizer <= -(affinity.nature - 50) * 10) {
                    mana.nature--
                }
            } else {
                if (randomizer <= level / 50 * affinity.nature + 1) {
                    mana.nature++
                }
            }

            if (affinity.magic < 50) {
                if (randomizer <= -(affinity.magic - 50) * 10) {
                    mana.magic--
                }
            } else {
                if (randomizer <= level / 50 * affinity.magic + 1) {
                    mana.magic++
                }
            }

            if (affinity.dark < 50) {
                if (randomizer <= -(affinity.dark - 50) * 10) {
                    mana.dark--
                }
            } else {
                if (randomizer <= level / 50 * affinity.dark + 1) {
                    mana.dark++
                }
            }
        }

        /**
         * Changes player's affinity according to its location on map.
         *
         * @param[player] the player.
         * @param[affinity] affinity data holder.
         * @param[level] player magic level.
         * @param[randomizer] random integer from 0 to 1000.
         */
        private fun playerLocationChangeAffinity(player: Player, affinity: ManaData, level: Int, randomizer: Int){

            if(randomizer > level) return

            if(player.location.biome == BiomeTypes.HELL) {
                affinity.fire++
            }

            if(player.location.biome.temperature < 0) {
                affinity.ice++
            }

            if(arrayOf("forest", "taiga", "jungle").any { it in player.location.biome.name }) {
                affinity.nature++
            }

            if(
                "water" in player.location.block.type.name ||
                arrayOf("river", "island, ocean").any { it in player.location.biome.name }
            ) {
                affinity.water++
            }

            if(player.position.y > 128 || player[Keys.IS_ELYTRA_FLYING].get()){
                affinity.air++
            }
        }
    }
}