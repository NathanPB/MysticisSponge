package dev.nathanpb.mysticis

import com.google.inject.Inject
import dev.nathanpb.mysticis.data.ManaData
import dev.nathanpb.mysticis.listener.ManaListener
import org.slf4j.Logger
import org.spongepowered.api.Game
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameConstructionEvent
import org.spongepowered.api.event.game.state.GameInitializationEvent
import org.spongepowered.api.event.game.state.GamePostInitializationEvent
import org.spongepowered.api.event.network.ClientConnectionEvent
import org.spongepowered.api.plugin.Dependency
import org.spongepowered.api.plugin.Plugin

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
 * Main class loaded by Sponge.
 *
 * @author NathanPB
 * @since 1.0.0
 */
@Plugin(
    id = "mysticissponge", name = "Mysticis Sponge", version = "1.0.0",
    dependencies = [
        Dependency(id = "spotlin", optional = false, version = "0.1.3")
    ]

)
class Mysticis {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var game: Game

    companion object {
        internal lateinit var instance: Mysticis
    }


    /**
     * Initialize the plugin instance on [instance].
     */
    @Listener
    fun onConstruction(e: GameConstructionEvent){
        Mysticis.instance = this
    }

    /**
     * Initialize some tasks.
     */
    @Listener
    fun onPostInit(e: GamePostInitializationEvent){
        ManaListener.taskPassiveChangePlayerData.submit(instance)
    }
}