---
name: execution
description: Execução reproduzível, evidências de validação e controle dos recursos iniciados pelo agente.
---

# Execução e validação

- Descubra comandos no perfil local, README, build e CI; confirme o diretório de execução. Prefira o wrapper versionado adequado ao sistema operacional.
- Execute a menor validação que prove o comportamento e os checks exigidos pelo módulo. Amplie para integrações afetadas. Não repita uma suíte sem mudança ou dúvida que justifique.
- Antes de alterar configuração, confira consumidores, deployment, pipelines e documentação relacionados. Não adote portas, perfis ou serviços de outro projeto.
- Só inicie serviços necessários. Use os mecanismos de isolamento existentes; quando suportado, prefira portas dinâmicas nos testes. Se a aplicação exigir porta fixa, verifique disponibilidade e configure os consumidores conforme o projeto.
- Registre diretório, comando, PID/container, porta e propriedade dos recursos criados nesta execução. Encerre apenas esses recursos, de forma graciosa, ao terminar ou quando possível após interrupção. Preserve processos e dados do usuário; não remova volumes ou bancos compartilhados.
- Registre comando/procedimento, resultado real e limitações. Se ambiente, ferramenta ou credencial faltar, indique `BLOQUEADO` ou `NÃO EXECUTADO`, com a causa e o próximo passo; não afirme aprovação.
- Preserve a causa das falhas. Não desabilite testes, reduza assertions ou altere metas para obter um resultado verde.
- Use dados sintéticos e remova segredos de logs, relatórios, capturas e fixtures. Mantenha somente evidências necessárias.
