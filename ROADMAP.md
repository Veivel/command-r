ROADMAP / TODO
- Add GitHub workflows for automatic release/publish
  - on PR: run tests, spotlessCheck, build (ci)
  - on new release tag (publish): publish new version on GitHub, add changelog from CHANGELOG.md, publish to modrinth
- Extend test suite
- Add support for up 2000 messages/commands (must persist somewhere)
- Add support for Ctrl+G "abort/restore" & Esc "disable search mode" functionality (thank Claude for raising this)
- Acknowledge potential compat issues: mods that modify command suggestions above chatbox, mods that allow multiple chats or send messages in the background