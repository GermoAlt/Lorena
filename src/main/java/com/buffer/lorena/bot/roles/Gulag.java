package com.buffer.lorena.bot.roles;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.ObjectAttachableListener;
import org.javacord.api.listener.channel.server.ServerChannelChangeOverwrittenPermissionsListener;
import org.javacord.api.listener.server.role.*;
import org.javacord.api.util.event.ListenerManager;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The type Gulag.
 */
public class Gulag implements Role {
    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public int getRawPosition() {
        return 0;
    }

    @Override
    public Optional<Color> getColor() {
        return Optional.empty();
    }

    @Override
    public boolean isMentionable() {
        return false;
    }

    @Override
    public boolean isDisplayedSeparately() {
        return false;
    }

    @Override
    public Collection<User> getUsers() {
        return null;
    }

    @Override
    public boolean hasUser(User user) {
        return false;
    }

    @Override
    public Permissions getPermissions() {
        return null;
    }

    @Override
    public boolean isManaged() {
        return false;
    }

    @Override
    public CompletableFuture<Void> delete() {
        return null;
    }

    @Override
    public int compareTo(Role o) {
        return 0;
    }

    @Override
    public DiscordApi getApi() {
        return null;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ListenerManager<ServerChannelChangeOverwrittenPermissionsListener> addServerChannelChangeOverwrittenPermissionsListener(ServerChannelChangeOverwrittenPermissionsListener listener) {
        return null;
    }

    @Override
    public List<ServerChannelChangeOverwrittenPermissionsListener> getServerChannelChangeOverwrittenPermissionsListeners() {
        return null;
    }

    @Override
    public ListenerManager<RoleChangePositionListener> addRoleChangePositionListener(RoleChangePositionListener listener) {
        return null;
    }

    @Override
    public List<RoleChangePositionListener> getRoleChangePositionListeners() {
        return null;
    }

    @Override
    public ListenerManager<UserRoleAddListener> addUserRoleAddListener(UserRoleAddListener listener) {
        return null;
    }

    @Override
    public List<UserRoleAddListener> getUserRoleAddListeners() {
        return null;
    }

    @Override
    public ListenerManager<RoleChangeMentionableListener> addRoleChangeMentionableListener(RoleChangeMentionableListener listener) {
        return null;
    }

    @Override
    public List<RoleChangeMentionableListener> getRoleChangeMentionableListeners() {
        return null;
    }

    @Override
    public ListenerManager<RoleChangeNameListener> addRoleChangeNameListener(RoleChangeNameListener listener) {
        return null;
    }

    @Override
    public List<RoleChangeNameListener> getRoleChangeNameListeners() {
        return null;
    }

    @Override
    public ListenerManager<RoleChangePermissionsListener> addRoleChangePermissionsListener(RoleChangePermissionsListener listener) {
        return null;
    }

    @Override
    public List<RoleChangePermissionsListener> getRoleChangePermissionsListeners() {
        return null;
    }

    @Override
    public ListenerManager<RoleDeleteListener> addRoleDeleteListener(RoleDeleteListener listener) {
        return null;
    }

    @Override
    public List<RoleDeleteListener> getRoleDeleteListeners() {
        return null;
    }

    @Override
    public ListenerManager<RoleChangeColorListener> addRoleChangeColorListener(RoleChangeColorListener listener) {
        return null;
    }

    @Override
    public List<RoleChangeColorListener> getRoleChangeColorListeners() {
        return null;
    }

    @Override
    public ListenerManager<UserRoleRemoveListener> addUserRoleRemoveListener(UserRoleRemoveListener listener) {
        return null;
    }

    @Override
    public List<UserRoleRemoveListener> getUserRoleRemoveListeners() {
        return null;
    }

    @Override
    public ListenerManager<RoleChangeHoistListener> addRoleChangeHoistListener(RoleChangeHoistListener listener) {
        return null;
    }

    @Override
    public List<RoleChangeHoistListener> getRoleChangeHoistListeners() {
        return null;
    }

    @Override
    public <T extends RoleAttachableListener & ObjectAttachableListener> Collection<ListenerManager<T>> addRoleAttachableListener(T listener) {
        return null;
    }

    @Override
    public <T extends RoleAttachableListener & ObjectAttachableListener> void removeRoleAttachableListener(T listener) {

    }

    @Override
    public <T extends RoleAttachableListener & ObjectAttachableListener> Map<T, List<Class<T>>> getRoleAttachableListeners() {
        return null;
    }

    @Override
    public <T extends RoleAttachableListener & ObjectAttachableListener> void removeListener(Class<T> listenerClass, T listener) {

    }
}
