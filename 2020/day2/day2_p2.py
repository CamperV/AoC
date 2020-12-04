import pdb

with open('input.txt', 'r') as tf:
    pol_pw = [(l.strip().split(':')[0], l.strip().split(':')[1]) for l in tf.readlines()]

valid = 0
for policy, pw_w in pol_pw:
    occurences, ch = policy.split(' ')

    # use pw_w w/ whitespace to shortcut the non-zero index situation
    pos1, pos2 = map(int, occurences.split('-'))

    if pw_w[pos1] != pw_w[pos2] and (pw_w[pos1] == ch or pw_w[pos2] == ch):
        valid += 1

print('---')
print(f'Total valid: {valid}/{len(pol_pw)}')
