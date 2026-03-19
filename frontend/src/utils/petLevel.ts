const levelThresholds = [0, 10, 25, 50, 100];

export function calcLevel(totalExp: number) {
  if (totalExp >= 100) return 5;
  if (totalExp >= 50) return 4;
  if (totalExp >= 25) return 3;
  if (totalExp >= 10) return 2;
  return 1;
}

export function calcCurrentExp(totalExp: number, level: number) {
  return totalExp - levelThresholds[level - 1];
}

export function nextLevelNeed(level: number) {
  if (level >= 5) return 0;
  return levelThresholds[level] - levelThresholds[level - 1];
}
