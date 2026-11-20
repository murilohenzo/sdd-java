# Validação para implementação — Reserva de estoque

- Data — 2026-11-20.
- Modo — Elephant-Goldfish. Um subagente em contexto novo, sem o histórico do autor, recebeu os caminhos das specs iniciais. A instrução limitou sua atuação à leitura e ao relatório. O código da aplicação ainda estava por escrever.
- Base independente — PRD, TechSpec e plano na revisão 1, blueprint correspondente, tarefas 1.0 e 2.0 e ADR 0001.
- Veredito inicial do revisor — **BLOQUEADO**.
- Veredito após reconciliação local — **PRONTO** para implementação da revisão 2.

## Achados e resolução

- **EG-01 · bloqueador · CA-01 a CA-03 / TC-01.** Fazer uma reserva final de quatro unidades provava apenas que o saldo era pelo menos quatro. O plano também deixava a reserva após conflitos sem confirmação. A revisão 2 exige uma prova do saldo exato, conflito para cada campo em separado e a recuperação da reserva original. TU-01 separa os cenários para identificar qual transição errou.
- **EG-02 · bloqueador · CA-04 / TC-02 a TC-04.** A checagem de status permitia mutações indevidas e não cobria explicitamente quantidade ausente ou os códigos pactuados. A revisão 2 verifica status e código, ausência de chamada ao dono do saldo para entradas inválidas e saldo intacto após cada rejeição de domínio.
- **EG-03 · bloqueador · RNF-02 / CA-06.** A base inicial prometia escuta local e descarte de estado no reinício sem uma verificação. O PRD 2 formaliza o aceite e MAN-01 liga a verificação operacional à tarefa 1.0.
- **EG-04 · melhoria · contrato de quantidade.** A representação do inteiro admitia duas interpretações em números como `6.0` ou `6e0`. O contrato agora exige representação inteira e cobre coerções em TC-02.

## Reconciliação

Os quatro achados foram confirmados pelos documentos. O autor atualizou PRD, blueprint, ADR, TechSpec e plano antes de implementar e conferiu apenas os vínculos mudados. Todos os CA vigentes têm verificação e tarefa. A dependência 2.0 → 1.0 é válida e acíclica. A revisão 2 preserva o limite de consistência por processo.

O agente local encontrou Maven no cache de ferramentas e Java 21 instalado, embora `mvn` estivesse ausente do PATH da sessão do revisor. A validação desta etapa foi documental. O [QA](qa.md) registra a evidência da implementação.

## Limite do piloto

O piloto observou três lacunas materiais e uma melhoria descobertas antes do código. A comparação A/B com validação local sobre a mesma base e a medição de tokens e tempo total ficam para uma rodada dedicada.
