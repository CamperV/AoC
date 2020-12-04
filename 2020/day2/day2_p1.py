import pdb

with open('input.txt', 'r') as tf:
    pol_pw = [(l.strip().split(':')[0], l.strip().split(':')[1]) for l in tf.readlines()]

valid = 0
for policy, pw in pol_pw:
    occurences, ch = policy.split(' ')
    min_occ, max_occ = map(int, occurences.split('-'))

    occ = pw.count(ch)
    if occ >= min_occ and occ <= max_occ:
        valid += 1

print('---')
print(f'Total valid: {valid}/{len(pol_pw)}')
