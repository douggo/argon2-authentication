package com.douggo.login.application.command;

import java.util.UUID;

public record UpdatePasswordCommand(UUID userId, String newPassword) {}
